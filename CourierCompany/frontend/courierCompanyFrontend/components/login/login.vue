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
const { $api } = useNuxtApp();
const router = useRouter();
// import { NuxtPage } from "#build/components";
// import { ref } from "vue";
const dialog = ref(true);

var userId = ref(""); //Reference to userId

const sendUser = () => {
  console.log("Usuario ingresado:", userId.value);

  $api
    .get("/users/" + userId.value, {
      headers: {},
    })
    .then((response) => {
      if (response.status === 200) {
        console.log("Usuario encontrado");
        return response.data;
      } else if (response.status === 404) {
        console.log("No existe el usuario");
      }
    })
    .then((userData) => {
      console.log("El usuario es un: ", userData.type);
      switch (userData.type) {
        case "administrador":
          //this.$router.push("/pages/administrador/index");
          navigateTo("/pages/administrador/index");
          break;
        case "recepcionista":
          //this.$router.push("/pages/reception/index");
          navigateTo("/pages/reception/index");
          break;
        case "operador":
          //this.$router.push("/pages/operation/index");
          navigateTo("/pages/operation/index");
          break;
        default:
          break;
      }
    })
    .catch((error) => {
      console.error("Error al enviar el usuario", error);
    });
};
</script>

<style lang="scss" scoped></style>
