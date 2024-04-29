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
    :items="billDetailsData"
    :sort-by="[{ key: 'id', order: 'asc' }]"
  >
    <template #top>
      <v-toolbar flat>
        <v-toolbar-title>Detalles de factura</v-toolbar-title>
        <v-divider class="mx-4" inset vertical></v-divider>
        <v-spacer></v-spacer>
        <v-dialog v-model="dialog" max-width="500px">
          <template v-slot:activator="{ props }">
            <v-btn class="mb-2" color="primary" dark v-bind="props">
              Añadir detalle
            </v-btn>
          </template>
          <v-card>
            <v-card-title class="text-center">
              <span>Información de Datos de factura</span>
            </v-card-title>
            <v-card-text>
              <v-container>
                <v-row>
                  <v-col cols="12" md="4" lg="6">
                    <v-text-field
                      v-model="editedItem.billId"
                      label="FacturaID"
                    ></v-text-field>
                  </v-col>
                  <v-col cols="12" md="4" lg="6">
                    <v-text-field
                      v-model="editedItem.packageId"
                      :disabled="editedItemIndex !== -1"
                      label="PaqueteID"
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
      <v-text-field
        v-model="editedItem.billId"
        label="FacturaID"
      ></v-text-field>
      <v-btn color="primary" @click="getBillDetailsData"> Buscar </v-btn>
    </template>
    <!-- <template v-slot:item.actions="{ item }">
      <v-icon class="me-2" size="small" @click="editItem(item)">
        mdi-pencil
      </v-icon>
    </template> -->
    <template v-slot:no-data>
      <v-btn color="primary" @click="initialize"> Reset </v-btn>
    </template>
  </v-data-table>
</template>

<script setup>
import { ref, onMounted } from "vue";
const { $api } = useNuxtApp();

const billDetailsData = ref([]);
const dialog = ref(false);
const editedItem = ref({});

const editedItemIndex = ref(-1);

//Snackbar
const infoSnackbar = ref({
  snackbar: false,
  message: "Snackbar message",
  color: "success",
});

const closeAndClearEdit = () => {
  dialog.value = false;
  editedItemIndex.value = -1;
  editedItem.value = {};
};

const headers = [
  { title: "FacturaID", value: "billId" },
  { title: "Fecha", value: "billDate" },
  { title: "ClienteID", value: "clientId" },
  { title: "PaqueteID", value: "packageId" },
  { title: "Costo de destino", value: "destinationCost" },
  { title: "Costo de peso", value: "weigthCost" },
  { title: "Costo total", value: "totalCost" },
  // { title: "Acciones", value: "actions", sortable: false },
];

onMounted(() => {});

//Setting information for the snackbar
const showBanner = () => {
  infoSnackbar.value.snackbar = true;
};

//Loading data
const billById = ref(false);
const loadData = async () => {
  await getBillDataById();
  console.log(billById.value);
  if (!billById.value) {
    console.log("Datos de factura no encontrado desde método loadData");
  } else {
    console.log("Datos de factura encontrado desde método loadData");
    createBillDetailData();
    billById.value = false;
  }
};

//Getting all data from the API
async function getBillDetailsData() {
  try {
    const response = await $api.get(
      "/bills/bill_details" + "?bill_id=" + editedItem.value.billId
    );
    console.log(response.data);

    if (response.status === 200) {
      console.log("Datos de facturas encontrados");
      billDetailsData.value = response.data;
      console.log(response.data);
    } else if (response.status === 404) {
      console.log("No existen Datos de facturas");
    } else {
      console.error(
        "Error al obtener los datos de los Datos de facturas. Estado de la respuesta:",
        response.status
      );
    }
  } catch (error) {
    if (error.response && error.response.status === 404) {
      console.log("No existen Datos de facturas");
    } else {
      console.error("Error al enviar la solicitud:", error);
    }
  }
}

//Getting data from the API by id
async function getBillDataById() {
  if (!editedItem.value.billId) {
    console.log("No se ha seleccionado un Datos de factura");
    return;
  }

  try {
    const response = await $api.get(
      "/bills/bill_details" + "?bill_id=" + editedItem.value.billId
    );

    if (response.status === 200) {
      console.log("Datos de factura encontrado");
      billById.value = true;
    } else {
      console.error(
        "Error al obtener los datos de los Datos de facturas. Estado de la respuesta:",
        response.status
      );
    }
  } catch (error) {
    if (error.response.status === 404) {
      console.log("No existe el Datos de factura");
      billById.value = false;
    } else {
      console.error("Error al enviar la solicitud:", error);
    }
  }
}

//Create data into the API
async function createBillDetailData() {
  console.log(editedItem.value);
  try {
    if (!editedItem.value.billId | !editedItem.value.packageId) {
      console.error("Hay campos vacíos");
      infoSnackbar.value.message = "Hay campos vacíos";
      infoSnackbar.value.color = "warning";
      showBanner();
      return;
    }
    const response = await $api.post(
      "/bills/bill_details" +
        "?bill_id=" +
        editedItem.value.billId +
        "&package_id=" +
        editedItem.value.packageId
    );

    if (response.status === 200) {
      console.log("Detalle creado exitosamente");
      getBillDetailsData();
      closeAndClearEdit();

      infoSnackbar.value.message = "Detalle creado exitosamente";
      infoSnackbar.value.color = "success";
      showBanner();
    } else if (response.status === 404) {
      console.log("No existe la factura");
    } else {
      console.error(
        "Error al obtener los datos de las facturas. Estado de la respuesta:",
        response.status
      );
    }
  } catch (error) {
    if (error.response && error.response.status === 404) {
      console.log("No existe el Factura");
    } else if (error.response && error.response.status === 500) {
      console.log("El Factura ya existe");
      infoSnackbar.value.message = "La factura ya existe";
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
</script>
