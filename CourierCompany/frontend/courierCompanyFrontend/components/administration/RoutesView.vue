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
    :items="routesData"
    :sort-by="[{ key: 'id', order: 'asc' }]"
  >
    <template #top>
      <v-toolbar flat>
        <v-toolbar-title>Rutas registradas</v-toolbar-title>
        <v-divider class="mx-4" inset vertical></v-divider>
        <v-spacer></v-spacer>
        <v-dialog v-model="dialog" max-width="500px">
          <template v-slot:activator="{ props }">
            <v-btn class="mb-2" color="primary" dark v-bind="props">
              Nueva ruta
            </v-btn>
          </template>
          <v-card>
            <v-card-title class="text-center">
              <span>Información de ruta</span>
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
                      v-model="editedItem.destinationId"
                      label="ID de destino"
                    ></v-text-field>
                  </v-col>
                  <v-col cols="12" md="4" lg="6">
                    <v-text-field
                      v-model="editedItem.packagesLimit"
                      label="Límite de paquetes"
                    ></v-text-field>
                  </v-col>
                  <v-col cols="12" md="4" lg="6">
                    <v-select
                      v-model="editedItem.status"
                      label="Estado"
                      :items="['activo', 'inactivo']"
                    >
                    </v-select>
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
        <v-dialog v-model="dialogDelete" max-width="500px">
          <v-card>
            <v-card-title class="text-center">
              <span>¿Estás seguro de eliminar esta ruta?</span></v-card-title
            >
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn
                color="blue-darken-1"
                variant="text"
                @click="closeAndClearDelete"
                >Cancelar</v-btn
              >
              <v-btn
                color="blue-darken-1"
                variant="text"
                @click="deleteControlPointDataById"
                >OK</v-btn
              >
              <v-spacer></v-spacer>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </v-toolbar>
    </template>
    <template v-slot:item.actions="{ item }">
      <v-icon class="me-2" size="small" @click="editItem(item)">
        mdi-pencil
      </v-icon>
      <v-icon size="small" @click="deleteItem(item)"> mdi-delete </v-icon>
    </template>
    <template v-slot:no-data>
      <v-btn color="primary" @click="initialize"> Reset </v-btn>
    </template>
  </v-data-table>
</template>

<script setup>
import { ref, onMounted } from "vue";
const { $api } = useNuxtApp();

const routesData = ref([]);
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
  editedItemIndex.value = routesData.value.indexOf(item);
  editedItem.value = Object.assign({}, item);
  dialog.value = true;
};

const deleteItem = (item) => {
  editedItemIndex.value = routesData.value.indexOf(item);
  editedItem.value = Object.assign({}, item);
  dialogDelete.value = true;
};

const closeAndClearEdit = () => {
  dialog.value = false;
  editedItemIndex.value = -1;
  editedItem.value = {};
};

const closeAndClearDelete = () => {
  dialogDelete.value = false;
  editedItemIndex.value = -1;
  editedItem.value = {};
};

const headers = [
  { title: "ID", value: "id" },
  { title: "ID de destino", value: "destinationId" },
  { title: "Límite de paquetes", value: "packagesLimit" },
  { title: "Estado", value: "status" },
  { title: "Acciones", value: "actions", sortable: false },
];

onMounted(() => {
  getRoutesData();
});

//Setting information for the snackbar
const showBanner = () => {
  infoSnackbar.value.snackbar = true;
};

//Loading data
const routeById = ref(false);
const loadData = async () => {
  await getRouteById();
  console.log(routeById.value);
  if (!routeById.value) {
    console.log("Ruta no encontrada desde método loadData");
    createRouteData();
  } else {
    console.log("Ruta encontrada desde método loadData");
    updateRouteData();
    routeById.value = false;
  }
};

//Getting all data from the API
async function getRoutesData() {
  try {
    const response = await $api.get("/routes");
    console.log(response.data);

    if (response.status === 200) {
      console.log("Rutas encontradas");
      routesData.value = response.data;
      console.log(response.data);
    } else if (response.status === 404) {
      console.log("No existen rutas");
    } else {
      console.error(
        "Error al obtener los datos de las rutas. Estado de la respuesta:",
        response.status
      );
    }
  } catch (error) {
    if (error.response && error.response.status === 404) {
      console.log("No existen rutas");
    } else {
      console.error("Error al enviar la solicitud:", error);
    }
  }
}

//Getting data from the API by id
async function getRouteById() {
  if (!editedItem.value.id) {
    routeById.value = false;
    return;
  }

  try {
    const response = await $api.get("/routes/" + editedItem.value.id);

    if (response.status === 200) {
      console.log("Ruta encontrada");
      routeById.value = true;
    } else {
      console.error(
        "Error al obtener los datos de las rutas. Estado de la respuesta:",
        response.status
      );
    }
  } catch (error) {
    if (response.status === 404) {
      console.log("No existe la ruta");
      routeById.value = false;
    } else {
      console.error("Error al enviar la solicitud:", error);
    }
  }
}

//Create data into the API
async function createRouteData() {
  console.log(editedItem.value);
  try {
    if (
      !editedItem.value.destinationId ||
      !editedItem.value.packagesLimit ||
      !editedItem.value.status
    ) {
      console.error("Hay campos vacíos");
      infoSnackbar.value.message = "Hay campos vacíos";
      infoSnackbar.value.color = "warning";
      showBanner();
      return;
    }
    const response = await $api.post("/routes/", {
      destinationId: editedItem.value.destinationId,
      packagesLimit: editedItem.value.packagesLimit,
      status: editedItem.value.status,
    });

    if (response.status === 200) {
      console.log("Ruta creada exitosamente");
      closeAndClearEdit();
      getRoutesData();

      infoSnackbar.value.message = "Ruta creada exitosamente";
      infoSnackbar.value.color = "success";
      showBanner();
    } else if (response.status === 404) {
      console.log("No existe la ruta");
    } else {
      console.error(
        "Error al obtener los datos de las rutas. Estado de la respuesta:",
        response.status
      );
    }
  } catch (error) {
    if (error.response && error.response.status === 404) {
      console.log("No existe la ruta");
    } else if (error.response && error.response.status === 500) {
      console.log("La ruta ya existe");
      infoSnackbar.value.message = "La ruta ya existe";
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
async function updateRouteData() {
  console.log(editedItem.value);
  try {
    if (
      !editedItem.value.id ||
      !editedItem.value.destinationId ||
      !editedItem.value.packagesLimit ||
      !editedItem.value.status
    ) {
      console.error("Hay campos vacíos");
      infoSnackbar.value.message = "Hay campos vacíos";
      infoSnackbar.value.color = "warning";
      showBanner();
      return;
    }
    const response = await $api.put("/routes/" + editedItem.value.id, {
      destinationId: editedItem.value.destinationId,
      packagesLimit: editedItem.value.packagesLimit,
      status: editedItem.value.status,
    });

    if (response.status === 200) {
      console.log("Ruta editada exitosamente");
      closeAndClearEdit();
      getRoutesData();

      infoSnackbar.value.message = "Ruta editada exitosamente";
      infoSnackbar.value.color = "success";
      showBanner();
    } else if (response.status === 404) {
      console.log("No existe la ruta");
    } else {
      console.error(
        "Error al obtener los datos de las rutas. Estado de la respuesta:",
        response.status
      );
    }
  } catch (error) {
    if (error.response && error.response.status === 404) {
      console.log("No existe la ruta");
    } else {
      console.error("Error al enviar la solicitud:", error);
    }
  }
}

//Deleting data into the API
async function deleteControlPointDataById() {
  console.log(editedItem.value);
  try {
    if (
      !editedItem.value.id ||
      !editedItem.value.destinationId ||
      !editedItem.value.packagesLimit ||
      !editedItem.value.status
    ) {
      console.error("No se seleccionó una ruta");
      infoSnackbar.value.message = "No se seleccionó una ruta";
      infoSnackbar.value.color = "warning";
      showBanner();
      return;
    }
    const response = await $api.delete("/routes/" + editedItem.value.id);

    if (response.status === 200) {
      console.log("Ruta eliminada exitosamente");
      closeAndClearDelete();
      getRoutesData();

      infoSnackbar.value.message = "Ruta eliminada exitosamente";
      infoSnackbar.value.color = "info";
      showBanner();
    } else if (response.status === 404) {
      console.log("No existe la ruta");
    } else {
      console.error(
        "Error al obtener los datos de las rutas. Estado de la respuesta:",
        response.status
      );
    }
  } catch (error) {
    if (error.response && error.response.status === 404) {
      console.log("No existe la ruta");
    } else {
      console.error("Error al enviar la solicitud:", error);
    }
  }
}
</script>
