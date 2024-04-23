<template>
  <v-app id="inspire">
    <v-app-bar class="px-3" density="compact" flat>
      <v-spacer></v-spacer>

      <v-tabs color="grey-darken-2" centered>
        <v-tab class="pa-0 ma-0" style="min-width: 0px" />
        <v-tab-item />
        <v-tab
          v-for="link in links"
          :key="link.name"
          :text="link.name"
          :to="link.link"
        ></v-tab>
      </v-tabs>
      <v-spacer></v-spacer>

      <p>ID del usuario: {{ userId }}</p>
    </v-app-bar>

    <v-main class="bg-grey-lighten-3">
      <v-container><slot></slot></v-container>
    </v-main>
  </v-app>
</template>

<script setup>
let userId = "";
onMounted(() => {
  const { $locally } = useNuxtApp();
  const userData = $locally.getItem("userData");
  console.log(userData);
  if (!userData || userData[0].type !== "administrador") {
    navigateTo("/");
  } else {
    userId = userData[0].id;
  }
});

const links = [
  { name: "Usuarios", link: "/administration/users" },
  { name: "Rutas", link: "/administration/routes" },
  { name: "Puntos de control", link: "/administration/controlPoints" },
  { name: "Destinos", link: "/administration/destinies" },
  { name: "Tarifas globales", link: "/administration/globalCosts" },
];
</script>

<style lang="scss" scoped></style>
