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
    :items="billsData"
    :sort-by="[{ key: 'id', order: 'asc' }]"
  >
    <template #top>
      <v-toolbar flat>
        <v-toolbar-title>Facturas registradas</v-toolbar-title>
        <v-divider class="mx-4" inset vertical></v-divider>
        <v-spacer></v-spacer>
        <v-dialog v-model="dialog" max-width="500px">
          <template v-slot:activator="{ props }">
            <v-btn class="mb-2" color="primary" dark v-bind="props">
              Nueva factura
            </v-btn>
          </template>
          <v-card>
            <v-card-title class="text-center">
              <span>Información de Factura</span>
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
                      v-model="editedItem.date"
                      :disabled="editedItemIndex !== -1"
                      label="Fecha"
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
    <!-- <template v-slot:item.actions="{ item }">
      <v-icon class="me-2" size="small" @click="editItem(item)">
        mdi-pencil
      </v-icon>
    </template> -->
    <template v-slot:no-data>
      <v-btn color="primary" @click="initialize"> Reset </v-btn>
    </template>
  </v-data-table>
</template>

<script setup>
import { ref, onMounted } from "vue";
const { $api } = useNuxtApp();

const billsData = ref([]);
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
  editedItemIndex.value = billsData.value.indexOf(item);
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
  { title: "FacturaID", value: "id" },
  { title: "ClienteID", value: "clientId" },
  { title: "Fecha de facturación", value: "date" },
  // { title: "Acciones", value: "actions", sortable: false },
];

onMounted(() => {
  getBillsData();
});

//Setting information for the snackbar
const showBanner = () => {
  infoSnackbar.value.snackbar = true;
};

//Loading data
const billById = ref(false);
const loadData = async () => {
  await getBillDataById();
  console.log(billById.value);
  if (!billById.value) {
    console.log("Factura no encontrado desde método loadData");
    createBillData();
  } else {
    console.log("Factura encontrado desde método loadData");
    updateBillData();
    billById.value = false;
  }
};

//Getting all data from the API
async function getBillsData() {
  try {
    const response = await $api.get("/bills");
    console.log(response.data);

    if (response.status === 200) {
      console.log("Facturas encontrados");
      billsData.value = response.data;
      console.log(response.data);
    } else if (response.status === 404) {
      console.log("No existen Facturas");
    } else {
      console.error(
        "Error al obtener los datos de los Facturas. Estado de la respuesta:",
        response.status
      );
    }
  } catch (error) {
    if (error.response && error.response.status === 404) {
      console.log("No existen Facturas");
    } else {
      console.error("Error al enviar la solicitud:", error);
    }
  }
}

//Getting data from the API by id
async function getBillDataById() {
  try {
    const response = await $api.get("/bills/" + editedItem.value.id);

    if (response.status === 200) {
      console.log("Factura encontrado");
      billById.value = true;
    } else {
      console.error(
        "Error al obtener los datos de los Facturas. Estado de la respuesta:",
        response.status
      );
    }
  } catch (error) {
    if (error.response.status === 404) {
      console.log("No existe el Factura");
      billById.value = false;
    } else {
      console.error("Error al enviar la solicitud:", error);
    }
  }
}

//Create data into the API
async function createBillData() {
  console.log(editedItem.value);
  try {
    if (!editedItem.value.clientId | !editedItem.value.date) {
      console.error("Hay campos vacíos");
      infoSnackbar.value.message = "Hay campos vacíos";
      infoSnackbar.value.color = "warning";
      showBanner();
      return;
    }
    const response = await $api.post("/bills", {
      clientId: editedItem.value.clientId,
      date: editedItem.value.date,
    });

    if (response.status === 200) {
      console.log("Factura creada exitosamente");
      closeAndClearEdit();
      getBillsData();

      infoSnackbar.value.message = "Factura creada exitosamente";
      infoSnackbar.value.color = "success";
      showBanner();
    } else if (response.status === 404) {
      console.log("No existe la factura");
    } else {
      console.error(
        "Error al obtener los datos de las facturas. Estado de la respuesta:",
        response.status
      );
    }
  } catch (error) {
    if (error.response && error.response.status === 404) {
      console.log("No existe el Factura");
    } else if (error.response && error.response.status === 500) {
      console.log("El Factura ya existe");
      infoSnackbar.value.message = "La factura ya existe";
      infoSnackbar.value.color = "warning";
      showBanner();
    } else {
      console.error("Error al enviar la solicitud:", error);
      infoSnackbar.value.message = "Algo salió mal";
      infoSnackbar.value.color = "error";
      showBanner();
    }
  }
}

//Updating data into the API
async function updateBillData() {
  console.log(editedItem.value);
  try {
    if (
      !editedItem.value.id ||
      !editedItem.value.clientId ||
      !editedItem.value.date
    ) {
      console.error("Hay campos vacíos");
      infoSnackbar.value.message = "Hay campos vacíos";
      infoSnackbar.value.color = "warning";
      showBanner();
      return;
    }
    const response = await $api.put("/bills/" + editedItem.value.id, {
      billId: editedItem.value.id,
      clientId: editedItem.value.clientId,
      billDate: editedItem.value.date,
    });

    if (response.status === 200) {
      console.log("Factura editado exitosamente");
      closeAndClearEdit();
      getBillsData();

      infoSnackbar.value.message = "Factura editada exitosamente";
      infoSnackbar.value.color = "success";
      showBanner();
    } else if (response.status === 404) {
      console.log("No existe el Factura");
    } else {
      console.error(
        "Error al obtener los datos de los Facturas. Estado de la respuesta:",
        response.status
      );
    }
  } catch (error) {
    if (error.response && error.response.status === 404) {
      console.log("No existe el Factura");
    } else {
      console.error("Error al enviar la solicitud:", error);
    }
  }
}
</script>
