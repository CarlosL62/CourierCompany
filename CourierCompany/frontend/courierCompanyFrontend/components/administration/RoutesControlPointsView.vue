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
    :items="routesControlPointsData"
    :sort-by="[{ key: 'id', order: 'asc' }]"
  >
    <template #top>
      <v-toolbar flat>
        <v-toolbar-title>Puntos de control por ruta</v-toolbar-title>
        <v-divider class="mx-4" inset vertical></v-divider>
        <v-spacer></v-spacer>
        <v-dialog v-model="dialog" max-width="500px">
          <template v-slot:activator="{ props }">
            <v-btn class="mb-2" color="primary" dark v-bind="props">
              Añadir
            </v-btn>
          </template>
          <v-card>
            <v-card-title class="text-center">
              <span>Añadir punto de control</span>
            </v-card-title>

            <v-card-text>
              <v-container>
                <v-row>
                  <v-col cols="12" md="4" lg="6">
                    <v-text-field
                      v-model="editedItem.routeId"
                      label="RutaID"
                    ></v-text-field>
                  </v-col>
                  <v-col cols="12" md="4" lg="6">
                    <v-text-field
                      v-model="editedItem.controlPointId"
                      label="PuntoDeControlID"
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
        <v-dialog v-model="dialogDelete" max-width="500px">
          <v-card>
            <v-card-title class="text-center">
              <span
                >¿Estás seguro de eliminar esta relación?</span
              ></v-card-title
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
                @click="deleteRouteControlPointData"
                >OK</v-btn
              >
              <v-spacer></v-spacer>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </v-toolbar>
    </template>
    <template v-slot:item.actions="{ item }">
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

const routesControlPointsData = ref([]);
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
  editedItemIndex.value = routesControlPointsData.value.indexOf(item);
  editedItem.value = Object.assign({}, item);
  dialog.value = true;
};

const deleteItem = (item) => {
  editedItemIndex.value = routesControlPointsData.value.indexOf(item);
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
  { title: "ID de ruta", value: "routeId" },
  { title: "ID de punto de control", value: "controlPointId" },
  { title: "Acciones", value: "actions", sortable: false },
];

onMounted(() => {
  getRoutesControlPointsData();
});

//Setting information for the snackbar
const showBanner = () => {
  infoSnackbar.value.snackbar = true;
};

//Loading data
const routeControlPointById = ref(false);
const loadData = async () => {
  await getRouteControlPointById();
  console.log(routeControlPointById.value);
  if (!routeControlPointById.value) {
    console.log("Relación no encontrada desde método loadData");
    createRouteControlPointData();
  } else {
    console.log("Relación encontrada desde método loadData");
    infoSnackbar.value.message = "La relación ya existe";
    infoSnackbar.value.color = "info";
    showBanner();
    routeControlPointById.value = false;
  }
};

//Getting all data from the API
async function getRoutesControlPointsData() {
  try {
    const response = await $api.get("/routes_control_points");
    console.log(response.data);

    if (response.status === 200) {
      console.log("Relaciones encontradas");
      routesControlPointsData.value = response.data;
      console.log(response.data);
    } else if (response.status === 404) {
      console.log("No existen relaciones");
    } else {
      console.error(
        "Error al obtener los datos de las relaciones. Estado de la respuesta:",
        response.status
      );
    }
  } catch (error) {
    if (error.response && error.response.status === 404) {
      console.log("No existen relaciones");
    } else {
      console.error("Error al enviar la solicitud:", error);
    }
  }
}

//Getting data from the API by id
async function getRouteControlPointById() {
  console.log(editedItem.value);
  if (!editedItem.value.routeId || !editedItem.value.controlPointId) {
    routeControlPointById.value = false;
    return;
  }

  try {
    const response = await $api.get(
      "/routes_control_points" +
        "?route_id=" +
        editedItem.value.routeId +
        "&control_point_id=" +
        editedItem.value.controlPointId
    );

    if (response.status === 200) {
      console.log("Relación encontrada");
      routeControlPointById.value = true;
    } else {
      console.error(
        "Error al obtener los datos de las relación. Estado de la respuesta:",
        response.status
      );
    }
  } catch (error) {
    if (error.response.status === 404) {
      console.log("No existe la ruta");
      routeControlPointById.value = false;
    } else {
      console.error("Error al enviar la solicitud:", error);
    }
  }
}

//Create data into the API
async function createRouteControlPointData() {
  console.log(editedItem.value);
  try {
    if (!editedItem.value.routeId || !editedItem.value.controlPointId) {
      console.error("Hay campos vacíos");
      infoSnackbar.value.message = "Hay campos vacíos";
      infoSnackbar.value.color = "warning";
      showBanner();
      return;
    }
    const response = await $api.post("/routes_control_points", {
      routeId: editedItem.value.routeId,
      controlPointId: editedItem.value.controlPointId,
    });

    if (response.status === 200) {
      console.log("Relación creada exitosamente");
      closeAndClearEdit();
      getRoutesControlPointsData();

      infoSnackbar.value.message = "Relación creada exitosamente";
      infoSnackbar.value.color = "success";
      showBanner();
    } else if (response.status === 404) {
      console.log("No existe la ruta");
    } else {
      console.error(
        "Error al obtener los datos de las relaiciones. Estado de la respuesta:",
        response.status
      );
    }
  } catch (error) {
    if (error.response && error.response.status === 404) {
      console.log("No existe la relación");
    } else if (error.response && error.response.status === 500) {
      console.log("La relación ya existe");
      infoSnackbar.value.message = "La relación ya existe";
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

//Deleting data into the API
async function deleteRouteControlPointData() {
  console.log(editedItem.value);
  try {
    if (!editedItem.value.routeId || !editedItem.value.controlPointId) {
      console.error("No se seleccionó una relación");
      infoSnackbar.value.message = "No se seleccionó una relación";
      infoSnackbar.value.color = "warning";
      showBanner();
      return;
    }

    const response = await $api.delete(
      "/routes_control_points" +
        "?route_id=" +
        editedItem.value.routeId +
        "&control_point_id=" +
        editedItem.value.controlPointId
    );

    if (response.status === 200) {
      console.log("Relación eliminada exitosamente");
      closeAndClearDelete();
      getRoutesControlPointsData();

      infoSnackbar.value.message = "Relación eliminada exitosamente";
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
