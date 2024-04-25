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
    :items="control_pointsData"
    :sort-by="[{ key: 'id', order: 'asc' }]"
  >
    <template #top>
      <v-toolbar flat>
        <v-toolbar-title>Puntos de control registrados</v-toolbar-title>
        <v-divider class="mx-4" inset vertical></v-divider>
        <v-spacer></v-spacer>
        <v-dialog v-model="dialog" max-width="500px">
          <template v-slot:activator="{ props }">
            <v-btn class="mb-2" color="primary" dark v-bind="props">
              Nuevo punto de control
            </v-btn>
          </template>
          <v-card>
            <v-card-title class="text-center">
              <span>Información de punto de control</span>
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
                      v-model="editedItem.operatorId"
                      label="ID del operador"
                    ></v-text-field>
                  </v-col>
                  <v-col cols="12" md="4" lg="6">
                    <v-text-field
                      v-model="editedItem.packagesLimit"
                      label="Límite de paquetes"
                    ></v-text-field>
                  </v-col>
                  <v-col cols="12" md="4" lg="6">
                    <v-text-field
                      v-model="editedItem.operationCost"
                      label="Costo de operación"
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
                >¿Estás seguro de eliminar este punto de control?</span
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

const control_pointsData = ref([]);
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
  editedItemIndex.value = control_pointsData.value.indexOf(item);
  editedItem.value = Object.assign({}, item);
  dialog.value = true;
};

const deleteItem = (item) => {
  editedItemIndex.value = control_pointsData.value.indexOf(item);
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
  { title: "ID del operador", value: "operatorId" },
  { title: "Límite de paquetes", value: "packagesLimit" },
  { title: "Costo operacional", value: "operationCost" },
  { title: "Acciones", value: "actions", sortable: false },
];

onMounted(() => {
  getControlPointData();
});

//Setting information for the snackbar
const showBanner = () => {
  infoSnackbar.value.snackbar = true;
};

//Loading data
const controlPointById = ref(false);
const loadData = () => {
  getControlPointById();
  console.log(controlPointById.value);
  if (!controlPointById.value) {
    console.log("Punto de control no encontrado desde método loadData");
    createControlPointData();
  } else {
    console.log("Punto de control encontrado desde método loadData");
    updateControlPointData();
    controlPointById.value = false;
  }
};

//Getting all data from the API
async function getControlPointData() {
  try {
    const response = await $api.get("/control_points");
    console.log(response.data);

    if (response.status === 200) {
      console.log("Puntos de control encontrados");
      control_pointsData.value = response.data;
      console.log(response.data);
    } else if (response.status === 404) {
      console.log("No existen rutas");
    } else {
      console.error(
        "Error al obtener los datos de los puntos de control. Estado de la respuesta:",
        response.status
      );
    }
  } catch (error) {
    if (error.response && error.response.status === 404) {
      console.log("No existen puntos de control");
    } else {
      console.error("Error al enviar la solicitud:", error);
    }
  }
}

//Getting data from the API by id
async function getControlPointById() {
  if (!editedItem.value.id) {
    controlPointById.value = false;
    return;
  }

  try {
    const response = await $api.get("/control_points/" + editedItem.value.id);

    if (response.status === 200) {
      console.log("Punto de control encontrado");
      controlPointById.value = true;
    } else {
      console.error(
        "Error al obtener los datos de los puntos de control. Estado de la respuesta:",
        response.status
      );
    }
  } catch (error) {
    if (response.status === 404) {
      console.log("No existe el punto de control");
      controlPointById.value = false;
    } else {
      console.error("Error al enviar la solicitud:", error);
    }
  }
}

//Create data into the API
async function createControlPointData() {
  console.log(editedItem.value);
  try {
    if (
      !editedItem.value.operatorId ||
      !editedItem.value.packagesLimit ||
      !editedItem.value.operationCost
    ) {
      console.error("Hay campos vacíos");
      infoSnackbar.value.message = "Hay campos vacíos";
      infoSnackbar.value.color = "warning";
      showBanner();
      return;
    }
    const response = await $api.post("/control_points/", {
      operatorId: editedItem.value.operatorId,
      packagesLimit: editedItem.value.packagesLimit,
      operationCost: editedItem.value.operationCost,
    });

    if (response.status === 200) {
      console.log("Punto de control creado exitosamente");
      closeAndClearEdit();
      getControlPointData();

      infoSnackbar.value.message = "Punto de control creado exitosamente";
      infoSnackbar.value.color = "success";
      showBanner();
    } else if (response.status === 404) {
      console.log("No existe el punto de control");
    } else {
      console.error(
        "Error al obtener los datos de los puntos de control. Estado de la respuesta:",
        response.status
      );
    }
  } catch (error) {
    if (error.response && error.response.status === 404) {
      console.log("No existe el punto de control");
    } else if (error.response && error.response.status === 500) {
      console.log("El punto de control ya existe");
      infoSnackbar.value.message = "El punto de control ya existe";
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
async function updateControlPointData() {
  console.log(editedItem.value);
  try {
    if (
      !editedItem.value.id ||
      !editedItem.value.operatorId ||
      !editedItem.value.packagesLimit ||
      !editedItem.value.operationCost
    ) {
      console.error("Hay campos vacíos");
      infoSnackbar.value.message = "Hay campos vacíos";
      infoSnackbar.value.color = "warning";
      showBanner();
      return;
    }
    const response = await $api.put("/control_points/" + editedItem.value.id, {
      operatorId: editedItem.value.operatorId,
      packagesLimit: editedItem.value.packagesLimit,
      operationCost: editedItem.value.operationCost,
    });

    if (response.status === 200) {
      console.log("Punto de control editado exitosamente");
      closeAndClearEdit();
      getControlPointData();

      infoSnackbar.value.message = "Punto de control editado exitosamente";
      infoSnackbar.value.color = "success";
      showBanner();
    } else if (response.status === 404) {
      console.log("No existe el punto de control");
    } else {
      console.error(
        "Error al obtener los datos de los puntos de control. Estado de la respuesta:",
        response.status
      );
    }
  } catch (error) {
    if (error.response && error.response.status === 404) {
      console.log("No existe el punto de control");
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
      !editedItem.value.operatorId ||
      !editedItem.value.packagesLimit ||
      !editedItem.value.operationCost
    ) {
      console.error("No se seleccionó un punto de control");
      infoSnackbar.value.message = "No se seleccionó un punto de control";
      infoSnackbar.value.color = "warning";
      showBanner();
      return;
    }
    const response = await $api.delete(
      "/control_points/" + editedItem.value.id
    );

    if (response.status === 200) {
      console.log("Punto de control eliminado exitosamente");
      closeAndClearDelete();
      getControlPointData();

      infoSnackbar.value.message = "Punto de control eliminado exitosamente";
      infoSnackbar.value.color = "info";
      showBanner();
    } else if (response.status === 404) {
      console.log("No existe el punto de control");
    } else {
      console.error(
        "Error al obtener los datos de los puntos de control. Estado de la respuesta:",
        response.status
      );
    }
  } catch (error) {
    if (error.response && error.response.status === 404) {
      console.log("No existe el punto de control");
    } else {
      console.error("Error al enviar la solicitud:", error);
    }
  }
}
</script>
