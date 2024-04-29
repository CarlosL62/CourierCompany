<template>
  <v-snackbar
    v-model="infoSnackbar.snackbar"
    :color="infoSnackbar.color"
    :timeout="2000"
    variant="elevated"
    location="center"
  >
    <h3>{{ infoSnackbar.message }}</h3>
  </v-snackbar>
  <v-data-table
    :headers="headers"
    :items="packagesData"
    :sort-by="[{ key: 'id', order: 'asc' }]"
  >
    <template #top>
      <v-toolbar flat>
        <v-toolbar-title>Paquetes en destino sin entregar</v-toolbar-title>
        <v-divider class="mx-4" inset vertical></v-divider>
        <v-spacer></v-spacer>
        <v-dialog v-model="dialog" max-width="500px">
          <v-card>
            <v-card-title class="text-center">
              <span>Información de paquete</span>
            </v-card-title>

            <v-card-text>
              <v-container>
                <v-row>
                  <v-col cols="12" md="4" lg="6">
                    <v-text-field
                      v-model="editedItem.id"
                      :disabled="true"
                      label="ID"
                    ></v-text-field>
                  </v-col>
                  <v-col cols="12" md="4" lg="6">
                    <v-text-field
                      v-model="editedItem.clientId"
                      :disabled="editedItemIndex !== -1"
                      label="ClienteID"
                    ></v-text-field>
                  </v-col>
                  <v-col cols="12" md="4" lg="6">
                    <v-text-field
                      v-model="editedItem.destinationId"
                      :disabled="editedItemIndex !== -1"
                      label="DestinoID"
                    ></v-text-field>
                  </v-col>
                  <v-col cols="12" md="4" lg="6">
                    <v-select
                      v-model="editedItem.status"
                      :disabled="editedItemIndex === -1"
                      label="Estado"
                      :items="['enBodega', 'enRuta', 'enDestino', 'entregado']"
                    >
                    </v-select>
                  </v-col>
                  <v-col cols="12" md="4" lg="6">
                    <v-text-field
                      v-model="editedItem.weigth"
                      :disabled="editedItemIndex !== -1"
                      label="Peso"
                    ></v-text-field>
                  </v-col>
                  <v-col cols="12" md="4" lg="12">
                    <v-textarea
                      v-model="editedItem.description"
                      label="Descripción"
                      :disabled="editedItemIndex !== -1"
                      :rows="3"
                      :input-props="{ autoGrow: false }"
                    ></v-textarea>
                  </v-col>
                  <v-col cols="12" md="4" lg="6">
                    <v-text-field
                      v-model="editedItem.beginDate"
                      :disabled="editedItemIndex !== -1"
                      label="Fecha de entrada"
                    ></v-text-field>
                  </v-col>
                  <v-col cols="12" md="4" lg="6">
                    <v-text-field
                      v-model="editedItem.endDate"
                      :disabled="editedItemIndex === -1"
                      label="Fecha de entrega"
                    ></v-text-field>
                  </v-col>
                </v-row>
              </v-container>
            </v-card-text>

            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn
                color="blue-darken-1"
                variant="text"
                @click="closeAndClearEdit"
              >
                Cancelar
              </v-btn>
              <v-btn
                color="blue-darken-1"
                variant="text"
                @click="
                  console.log(editedItem);
                  loadData();
                "
              >
                Guardar
              </v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </v-toolbar>
    </template>
    <template v-slot:item.actions="{ item }">
      <v-icon class="me-2" size="small" @click="editItem(item)">
        mdi-pencil
      </v-icon>
    </template>
    <template v-slot:no-data>
      <v-btn color="primary" @click="initialize"> Reset </v-btn>
    </template>
  </v-data-table>
</template>

<script setup>
import { ref, onMounted } from "vue";
const { $api } = useNuxtApp();

const packagesData = ref([]);
const dialog = ref(false);
const dialogDelete = ref(false);
const editedItem = ref({});

const editedItemIndex = ref(-1);

//Snackbar
const infoSnackbar = ref({
  snackbar: false,
  message: "Snackbar message",
  color: "success",
});

const editItem = (item) => {
  editedItemIndex.value = packagesData.value.indexOf(item);
  editedItem.value = Object.assign({}, item);
  console.log(editedItem.value);
  dialog.value = true;
};

const closeAndClearEdit = () => {
  dialog.value = false;
  editedItemIndex.value = -1;
  editedItem.value = {};
};

const headers = [
  { title: "ID", value: "id" },
  { title: "ClienteID", value: "clientId" },
  { title: "DestinoID ", value: "destinationId" },
  { title: "Estado", value: "status" },
  { title: "Peso", value: "weigth" },
  { title: "descripcion", value: "description" },
  { title: "Fecha de entrada", value: "beginDate" },
  { title: "Fecha de entrega", value: "endDate" },
  { title: "Acciones", value: "actions", sortable: false },
];

onMounted(() => {
  getPackagesData();
});

//Setting information for the snackbar
const showBanner = () => {
  infoSnackbar.value.snackbar = true;
};

//Loading data
const packageById = ref(false);
const loadData = async () => {
  await getPackageDataById();
  console.log(packageById.value);
  if (!packageById.value) {
    console.log("paquete no encontrado desde método loadData");
  } else {
    console.log("paquete encontrado desde método loadData");
    updatePackageData();
    packageById.value = false;
  }
};

//Getting all data from the API
async function getPackagesData() {
  try {
    const response = await $api.get("/packages?status=enDestino");
    console.log(response.data);

    if (response.status === 200) {
      console.log("Paquetes encontrados");
      packagesData.value = response.data;
      console.log(response.data);
    } else if (response.status === 404) {
      console.log("No existen Paquetes");
    } else {
      console.error(
        "Error al obtener los datos de los Paquetes. Estado de la respuesta:",
        response.status
      );
    }
  } catch (error) {
    if (error.response && error.response.status === 404) {
      console.log("No existen Paquetes");
    } else {
      console.error("Error al enviar la solicitud:", error);
    }
  }
}

//Getting data from the API by id
async function getPackageDataById() {
  if (!editedItem.value.id) {
    console.log("No se ha seleccionado un paquete");
    return;
  }

  try {
    const response = await $api.get("/packages/" + editedItem.value.id);

    if (response.status === 200) {
      console.log("paquete encontrado");
      packageById.value = true;
    } else {
      console.error(
        "Error al obtener los datos de los Paquetes. Estado de la respuesta:",
        response.status
      );
    }
  } catch (error) {
    if (error.response.status === 404) {
      console.log("No existe el paquete");
      packageById.value = false;
    } else {
      console.error("Error al enviar la solicitud:", error);
    }
  }
}

//Updating data into the API
async function updatePackageData() {
  console.log(editedItem.value);
  try {
    if (
      !editedItem.value.id ||
      !editedItem.value.clientId ||
      !editedItem.value.destinationId ||
      !editedItem.value.status ||
      !editedItem.value.weigth ||
      !editedItem.value.description ||
      !editedItem.value.beginDate ||
      !editedItem.value.endDate
    ) {
      console.error("Hay campos vacíos");
      infoSnackbar.value.message = "Hay campos vacíos";
      infoSnackbar.value.color = "warning";
      showBanner();
      return;
    }
    const response = await $api.put("/packages/" + editedItem.value.id, {
      clientId: editedItem.value.clientId,
      destinationId: editedItem.value.destinationId,
      status: editedItem.value.status,
      weigth: editedItem.value.weigth,
      description: editedItem.value.description,
      beginDate: editedItem.value.beginDate,
      endDate: editedItem.value.endDate,
    });

    if (response.status === 200) {
      console.log("paquete editado exitosamente");
      closeAndClearEdit();
      getPackagesData();

      infoSnackbar.value.message = "paquete editado exitosamente";
      infoSnackbar.value.color = "success";
      showBanner();
    } else if (response.status === 404) {
      console.log("No existe el paquete");
    } else {
      console.error(
        "Error al obtener los datos de los Paquetes. Estado de la respuesta:",
        response.status
      );
    }
  } catch (error) {
    if (error.response && error.response.status === 404) {
      console.log("No existe el paquete");
    } else {
      console.error("Error al enviar la solicitud:", error);
    }
  }
}
</script>
