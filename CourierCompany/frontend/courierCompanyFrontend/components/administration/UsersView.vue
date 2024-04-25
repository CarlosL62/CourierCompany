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
    :items="usersData"
    :sort-by="[{ key: 'id', order: 'asc' }]"
  >
    <template #top>
      <v-toolbar flat>
        <v-toolbar-title>Usuarios registrados</v-toolbar-title>
        <v-divider class="mx-4" inset vertical></v-divider>
        <v-spacer></v-spacer>
        <v-dialog v-model="dialog" max-width="500px">
          <template v-slot:activator="{ props }">
            <v-btn class="mb-2" color="primary" dark v-bind="props">
              Nuevo usuario
            </v-btn>
          </template>
          <v-card>
            <v-card-title class="text-center">
              <span>Información de usuario</span>
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
                    <v-select
                      v-model="editedItem.type"
                      label="Tipo"
                      :items="['administrador', 'recepcionista', 'operador']"
                    >
                    </v-select>
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
              <span>¿Estás seguro de eliminar este usuario?</span></v-card-title
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

const usersData = ref([]);
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
  editedItemIndex.value = usersData.value.indexOf(item);
  editedItem.value = Object.assign({}, item);
  dialog.value = true;
};

const deleteItem = (item) => {
  editedItemIndex.value = usersData.value.indexOf(item);
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
  { title: "Nombre", value: "name" },
  { title: "Tipo", value: "type" },
  { title: "Estado", value: "status" },
  { title: "Acciones", value: "actions", sortable: false },
];

onMounted(() => {
  getUsersData();
});

//Setting information for the snackbar
const showBanner = () => {
  infoSnackbar.value.snackbar = true;
};

//Loading data
const userById = ref(false);
const loadData = () => {
  getUserDataById();
  console.log(userById.value);
  if (!userById.value) {
    console.log("Usuario no encontrado desde método loadData");
    createUserData();
  } else {
    console.log("Usuario encontrado desde método loadData");
    updateUserData();
    userById.value = false;
  }
};

//Getting all data from the API
async function getUsersData() {
  try {
    const response = await $api.get("/users");
    console.log(response.data);

    if (response.status === 200) {
      console.log("Usuarios encontrados");
      usersData.value = response.data;
      console.log(response.data);
    } else if (response.status === 404) {
      console.log("No existen usuarios");
    } else {
      console.error(
        "Error al obtener los datos de los usuarios. Estado de la respuesta:",
        response.status
      );
    }
  } catch (error) {
    if (error.response && error.response.status === 404) {
      console.log("No existen usuarios");
    } else {
      console.error("Error al enviar la solicitud:", error);
    }
  }
}

//Getting data from the API by id
async function getUserDataById() {
  try {
    const response = await $api.get("/users/" + editedItem.value.id);

    if (response.status === 200) {
      console.log("Usuario encontrado");
      userById.value = true;
    } else {
      console.error(
        "Error al obtener los datos de los usuarios. Estado de la respuesta:",
        response.status
      );
    }
  } catch (error) {
    if (response.status === 404) {
      console.log("No existe el usuario");
      userById.value = false;
    } else {
      console.error("Error al enviar la solicitud:", error);
    }
  }
}

//Create data into the API
async function createUserData() {
  console.log(editedItem.value);
  try {
    if (
      !editedItem.value.id ||
      !editedItem.value.name ||
      !editedItem.value.type ||
      !editedItem.value.status
    ) {
      console.error("Hay campos vacíos");
      infoSnackbar.value.message = "Hay campos vacíos";
      infoSnackbar.value.color = "warning";
      showBanner();
      return;
    }
    const response = await $api.post("/users", {
      id: editedItem.value.id,
      name: editedItem.value.name,
      type: editedItem.value.type,
      status: editedItem.value.status,
    });

    if (response.status === 200) {
      console.log("Usuario creado exitosamente");
      closeAndClearEdit();
      getUsersData();

      infoSnackbar.value.message = "Usuario creado exitosamente";
      infoSnackbar.value.color = "success";
      showBanner();
    } else if (response.status === 404) {
      console.log("No existe el usuario");
    } else {
      console.error(
        "Error al obtener los datos de los usuarios. Estado de la respuesta:",
        response.status
      );
    }
  } catch (error) {
    if (error.response && error.response.status === 404) {
      console.log("No existe el usuario");
    } else if (error.response && error.response.status === 500) {
      console.log("El usuario ya existe");
      infoSnackbar.value.message = "El usuario ya existe";
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
async function updateUserData() {
  console.log(editedItem.value);
  try {
    if (
      !editedItem.value.id ||
      !editedItem.value.name ||
      !editedItem.value.type ||
      !editedItem.value.status
    ) {
      console.error("Hay campos vacíos");
      infoSnackbar.value.message = "Hay campos vacíos";
      infoSnackbar.value.color = "warning";
      showBanner();
      return;
    }
    const response = await $api.put("/users/" + editedItem.value.id, {
      name: editedItem.value.name,
      type: editedItem.value.type,
      status: editedItem.value.status,
    });

    if (response.status === 200) {
      console.log("Usuario editado exitosamente");
      closeAndClearEdit();
      getUsersData();

      infoSnackbar.value.message = "Usuario editado exitosamente";
      infoSnackbar.value.color = "success";
      showBanner();
    } else if (response.status === 404) {
      console.log("No existe el usuario");
    } else {
      console.error(
        "Error al obtener los datos de los usuarios. Estado de la respuesta:",
        response.status
      );
    }
  } catch (error) {
    if (error.response && error.response.status === 404) {
      console.log("No existe el usuario");
    } else {
      console.error("Error al enviar la solicitud:", error);
    }
  }
}

//Deleting data into the API
async function deleteUserDataById() {
  console.log(editedItem.value);
  try {
    if (
      !editedItem.value.id ||
      !editedItem.value.name ||
      !editedItem.value.type ||
      !editedItem.value.status
    ) {
      console.error("No se seleccionó un usuario");
      infoSnackbar.value.message = "No se seleccionó un usuario";
      infoSnackbar.value.color = "warning";
      showBanner();
      return;
    }
    const response = await $api.delete("/users/" + editedItem.value.id);

    if (response.status === 200) {
      console.log("Usuario eliminado exitosamente");
      closeAndClearDelete();
      getUsersData();

      infoSnackbar.value.message = "Usuario eliminado exitosamente";
      infoSnackbar.value.color = "info";
      showBanner();
    } else if (response.status === 404) {
      console.log("No existe el usuario");
    } else {
      console.error(
        "Error al obtener los datos de los usuarios. Estado de la respuesta:",
        response.status
      );
    }
  } catch (error) {
    if (error.response && error.response.status === 404) {
      console.log("No existe el usuario");
    } else {
      console.error("Error al enviar la solicitud:", error);
    }
  }
}
</script>
