<template>
  <h1>Inicie Sesión</h1>
  <div>
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
        @click="sendUser"
      >
        Log In
      </v-btn>
    </v-card>
  </div>
</template>

<script setup>
import { ref } from "vue";
const dialog = ref(true);

var userId = ref(""); //Reference to userId

const sendUser = () => {
  console.log("Usuario ingresado:", userId.value);
  // Aquí puedes llamar a una función que envíe el usuario a un servidor, por ejemplo
  // enviarUsuarioAServidor(user.value);

  fetch("http://localhost:8080/CourierCompany_war/user/" + userId.value, {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
    },
  })
    .then((response) => {
      if (response.ok) {
        console.log("Usuario encontrado");
        return response.text();
      } else if (response.status == 404) {
        console.log("No existe el usuario");
      }
    })
    .then((responseData) => {
      const userData = JSON.parse(responseData);
      console.log("El usuario es un: ", userData[0].type);
    })
    .catch((error) => {
      console.error("Error al enviar el usuario", error);
    });
};
</script>

<style lang="scss" scoped></style>
