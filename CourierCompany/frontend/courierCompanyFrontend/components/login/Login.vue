<template>
  <div>
    <h1 id="is">Inicio de Sesión</h1>
    <v-card
      class="mx-auto pa-12 pb-8"
      elevation="8"
      max-width="448"
      rounded="lg"
    >
      <div class="text-subtitle-1 text-medium-emphasis">ID del usuario</div>

      <v-text-field
        v-model="userId"
        density="compact"
        placeholder="UsuarioID"
        prepend-inner-icon="mdi mdi-account"
        variant="outlined"
      ></v-text-field>

      <v-btn
        class="mb-8"
        color="blue"
        size="large"
        variant="tonal"
        block
        @click="fetchUserData"
      >
        Log In
      </v-btn>
    </v-card>

    <div v-if="showAlert" class="alert">{{ alertMessage }}</div>
  </div>
</template>

<script setup>
import { ref } from "vue";

const showAlert = ref(false);
const alertMessage = ref("");

const { $api } = useNuxtApp();
// import { NuxtPage } from "#build/components";
const dialog = ref(true);

const userId = ref(""); //Reference to userId

async function fetchUserData() {
  try {
    const response = await $api.get("/users/" + userId.value, {
      headers: {},
    });

    if (response.status === 200) {
      console.log("Usuario encontrado");
      const userData = response.data;
      const { $locally } = useNuxtApp();
      $locally.setItem("userData", JSON.stringify(userData));
      console.log(response.data);
      console.log("El usuario es un: ", userData[0].type);

      switch (userData[0].type) {
        case "administrador":
          navigateTo("/administration/");
          break;
        case "recepcionista":
          navigateTo("/reception/");
          break;
        case "operador":
          //router.push("/operation/");
          navigateTo("/operation/");
          break;
        default:
          console.log("Tipo de usuario no reconocido");
          break;
      }
    } else if (response.status === 404) {
      console.log("No existe el usuario");
      showAlert.value = true;
      alertMessage.value = "No existe el usuario" + userId.value;
    } else {
      console.error(
        "Error al obtener los datos del usuario. Estado de la respuesta:",
        response.status
      );
    }
  } catch (error) {
    if (error.response && error.response.status === 404) {
      console.log("No existe el usuario");
      showAlert.value = true;
      alertMessage.value = "No existe el usuario";
    } else {
      console.error("Error al enviar la solicitud:", error);
      // Aquí podrías manejar el error de alguna manera, como mostrar un mensaje de error al usuario
    }
  }
}
</script>

<style lang="scss" scoped>
#is {
  text-align: center;
  font-size: 2.5rem;
  margin-top: 2rem;
}
</style>
