<template>
  <div>
    <h1>Paquetes</h1>
  </div>
  <ReceptionPackagesView />
  <ReceptionDeliveredPackagesView />
  <ReceptionLocationOfPackagesView />
</template>

<script setup>
definePageMeta({
  layout: "reception",
});
</script>

<style lang="scss" scoped></style>
