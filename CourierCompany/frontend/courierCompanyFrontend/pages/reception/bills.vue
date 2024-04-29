<template>
  <div>
    <h1>Facturas</h1>
  </div>
  <ReceptionBillsView />
  <ReceptionBillDetailsView />
</template>

<script setup>
definePageMeta({
  layout: "reception",
});
</script>

<style lang="scss" scoped></style>
