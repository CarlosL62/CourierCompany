<template>
  <h1 v-if="userData">Bienvenido {{ userData[0].name }}</h1>
</template>

<script setup>
definePageMeta({
  layout: "reception",
});

const { $locally } = useNuxtApp();
const userData = $locally.getItem("userData");
</script>

<style lang="scss" scoped>
h1 {
  text-align: center;
  margin-top: 100px;
}
</style>
