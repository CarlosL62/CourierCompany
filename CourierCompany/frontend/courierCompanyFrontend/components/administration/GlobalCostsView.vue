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
    :items="global_costsData"
    :sort-by="[{ key: 'id', order: 'asc' }]"
  >
    <template #top>
      <v-toolbar flat>
        <v-toolbar-title>Tarifas globlales registradas</v-toolbar-title>
        <v-divider class="mx-4" inset vertical></v-divider>
        <v-spacer></v-spacer>
        <v-dialog v-model="dialog" max-width="500px">
          <v-card>
            <v-card-title class="text-center">
              <span>Información de tarifas globlales</span>
            </v-card-title>

            <v-card-text>
              <v-container>
                <v-row>
                  <v-col cols="12" md="4" lg="6">
                    <v-text-field
                      v-model="editedItem.controlPointCost"
                      label="Costo Costo global"
                    ></v-text-field>
                  </v-col>
                  <v-col cols="12" md="4" lg="6">
                    <v-text-field
                      v-model="editedItem.weigthCost"
                      label="Costo Peso de paquete"
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

const global_costsData = ref([]);
const dialog = ref(false);
const editedItem = ref({});

const editedItemIndex = ref(-1);

//Snackbar
const infoSnackbar = ref({
  snackbar: false,
  message: "Snackbar message",
  color: "success",
});

const editItem = (item) => {
  editedItemIndex.value = global_costsData.value.indexOf(item);
  editedItem.value = Object.assign({}, item);
  dialog.value = true;
};

const closeAndClearEdit = () => {
  dialog.value = false;
  editedItemIndex.value = -1;
  editedItem.value = {};
};

const headers = [
  { title: "Costo global de punto de control", value: "controlPointCost" },
  { title: "Costo global de peso del paquete", value: "weigthCost" },
  { title: "Acciones", value: "actions", sortable: false },
];

onMounted(() => {
  getGlobalCosts();
});

//Setting information for the snackbar
const showBanner = () => {
  infoSnackbar.value.snackbar = true;
};

//Loading data
const loadData = async () => {
  await updateGlobalCosts();
};

//Getting all data from the API
async function getGlobalCosts() {
  try {
    const response = await $api.get("/global_costs");
    console.log(response.data);

    if (response.status === 200) {
      console.log("Costos globales encontrados");
      global_costsData.value = response.data;
      console.log(response.data);
    } else if (response.status === 404) {
      console.log("No existen costos globales");
    } else {
      console.error(
        "Error al obtener los datos de los Costos globales. Estado de la respuesta:",
        response.status
      );
    }
  } catch (error) {
    if (error.response && error.response.status === 404) {
      console.log("No existen Costos globales");
    } else {
      console.error("Error al enviar la solicitud:", error);
    }
  }
}

//Updating data into the API
async function updateGlobalCosts() {
  console.log(editedItem.value);
  try {
    if (!editedItem.value.controlPointCost || !editedItem.value.weigthCost) {
      console.error("Hay campos vacíos");
      infoSnackbar.value.message = "Hay campos vacíos";
      infoSnackbar.value.color = "warning";
      showBanner();
      return;
    }
    const response = await $api.put("/global_costs", {
      controlPointCost: editedItem.value.controlPointCost,
      weigthCost: editedItem.value.weigthCost,
    });

    if (response.status === 200) {
      console.log("Costo global editado exitosamente");
      closeAndClearEdit();
      getGlobalCosts();

      infoSnackbar.value.message = "Costo global editado exitosamente";
      infoSnackbar.value.color = "success";
      showBanner();
    } else if (response.status === 404) {
      console.log("No existe el Costo global");
    } else {
      console.error(
        "Error al obtener los datos de los Costos globales. Estado de la respuesta:",
        response.status
      );
    }
  } catch (error) {
    if (error.response && error.response.status === 404) {
      console.log("No existe el Costo global");
    } else {
      console.error("Error al enviar la solicitud:", error);
    }
  }
}
</script>
