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
    :items="clientsData"
    :sort-by="[{ key: 'id', order: 'asc' }]"
  >
    <template #top>
      <v-toolbar flat>
        <v-toolbar-title>Clientes registrados</v-toolbar-title>
        <v-divider class="mx-4" inset vertical></v-divider>
        <v-spacer></v-spacer>
        <v-dialog v-model="dialog" max-width="500px">
          <template v-slot:activator="{ props }">
            <v-btn class="mb-2" color="primary" dark v-bind="props">
              Nuevo cliente
            </v-btn>
          </template>
          <v-card>
            <v-card-title class="text-center">
              <span>Información de cliente</span>
            </v-card-title>

            <v-card-text>
              <v-container>
                <v-row>
                  <v-col cols="12" md="4" lg="6">
                    <v-text-field
                      v-model="editedItem.id"
                      :disabled="editedItemIndex !== -1"
                      label="ID"
                    ></v-text-field>
                  </v-col>
                  <v-col cols="12" md="4" lg="6">
                    <v-text-field
                      v-model="editedItem.name"
                      label="Nombre"
                    ></v-text-field>
                  </v-col>
                  <v-col cols="12" md="4" lg="6">
                    <v-text-field
                      v-model="editedItem.phoneNumber"
                      label="Teléfono"
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
              <span>¿Estás seguro de eliminar este cliente?</span></v-card-title
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
                @click="deleteUserDataById"
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
    </template>
    <template v-slot:no-data>
      <v-btn color="primary" @click="initialize"> Reset </v-btn>
    </template>
  </v-data-table>
</template>

<script setup>
import { ref, onMounted } from "vue";
const { $api } = useNuxtApp();

const clientsData = ref([]);
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
  editedItemIndex.value = clientsData.value.indexOf(item);
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
  { title: "Nombre", value: "name" },
  { title: "Número de teléfono", value: "phoneNumber" },
  { title: "Acciones", value: "actions", sortable: false },
];

onMounted(() => {
  getClientsData();
});

//Setting information for the snackbar
const showBanner = () => {
  infoSnackbar.value.snackbar = true;
};

//Loading data
const clientById = ref(false);
const loadData = async () => {
  await getClientById();
  console.log(clientById.value);
  if (!clientById.value) {
    console.log("cliente no encontrado desde método loadData");
    createClientData();
  } else {
    console.log("cliente encontrado desde método loadData");
    updateClientData();
    clientById.value = false;
  }
};

//Getting all data from the API
async function getClientsData() {
  try {
    const response = await $api.get("/clients");
    console.log(response.data);

    if (response.status === 200) {
      console.log("Clientes encontrados");
      clientsData.value = response.data;
      console.log(response.data);
    } else if (response.status === 404) {
      console.log("No existen Clientes");
    } else {
      console.error(
        "Error al obtener los datos de los Clientes. Estado de la respuesta:",
        response.status
      );
    }
  } catch (error) {
    if (error.response && error.response.status === 404) {
      console.log("No existen Clientes");
    } else {
      console.error("Error al enviar la solicitud:", error);
    }
  }
}

//Getting data from the API by id
async function getClientById() {
  try {
    const response = await $api.get("/clients/" + editedItem.value.id);

    if (response.status === 200) {
      console.log("cliente encontrado");
      clientById.value = true;
    } else {
      console.error(
        "Error al obtener los datos de los Clientes. Estado de la respuesta:",
        response.status
      );
    }
  } catch (error) {
    if (error.response?.status === 404) {
      console.log("No existe el cliente");
      clientById.value = false;
    } else {
      console.error("Error al enviar la solicitud:", error);
    }
  }
}

//Create data into the API
async function createClientData() {
  console.log(editedItem.value);
  try {
    if (
      !editedItem.value.id ||
      !editedItem.value.name ||
      !editedItem.value.phoneNumber
    ) {
      console.error("Hay campos vacíos");
      infoSnackbar.value.message = "Hay campos vacíos";
      infoSnackbar.value.color = "warning";
      showBanner();
      return;
    }
    const response = await $api.post("/clients", {
      id: editedItem.value.id,
      name: editedItem.value.name,
      phoneNumber: editedItem.value.phoneNumber,
    });

    if (response.status === 200) {
      console.log("cliente creado exitosamente");
      closeAndClearEdit();
      getClientsData();

      infoSnackbar.value.message = "Cliente creado exitosamente";
      infoSnackbar.value.color = "success";
      showBanner();
    } else if (response.status === 404) {
      console.log("No existe el cliente");
    } else {
      console.error(
        "Error al obtener los datos de los Clientes. Estado de la respuesta:",
        response.status
      );
    }
  } catch (error) {
    if (error.response && error.response.status === 404) {
      console.log("No existe el cliente");
    } else if (error.response && error.response.status === 500) {
      console.log("El cliente ya existe");
      infoSnackbar.value.message = "El cliente ya existe";
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
async function updateClientData() {
  console.log(editedItem.value);
  try {
    if (
      !editedItem.value.id ||
      !editedItem.value.name ||
      !editedItem.value.phoneNumber
    ) {
      console.error("Hay campos vacíos");
      infoSnackbar.value.message = "Hay campos vacíos";
      infoSnackbar.value.color = "warning";
      showBanner();
      return;
    }
    const response = await $api.put("/clients/" + editedItem.value.id, {
      name: editedItem.value.name,
      phoneNumber: editedItem.value.phoneNumber,
    });

    if (response.status === 200) {
      console.log("cliente editado exitosamente");
      closeAndClearEdit();
      getClientsData();

      infoSnackbar.value.message = "Cliente editado exitosamente";
      infoSnackbar.value.color = "success";
      showBanner();
    } else if (response.status === 404) {
      console.log("No existe el cliente");
    } else {
      console.error(
        "Error al obtener los datos de los Clientes. Estado de la respuesta:",
        response.status
      );
    }
  } catch (error) {
    if (error.response && error.response.status === 404) {
      console.log("No existe el cliente");
    } else {
      console.error("Error al enviar la solicitud:", error);
    }
  }
}
</script>
