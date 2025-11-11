<template>
  <v-app>
    <v-app-bar color="primary" prominent>
      <v-app-bar-title>Bane - Client Migration Tool</v-app-bar-title>
      <v-spacer></v-spacer>
      <v-btn @click="loadData" :loading="loading" variant="outlined">
        <v-icon icon="mdi-refresh"></v-icon>
        Refresh Data
      </v-btn>
    </v-app-bar>

    <v-main>
      <v-container fluid>
        <MigrationTool />
      </v-container>
    </v-main>
  </v-app>
</template>

<script>
import MigrationTool from './components/MigrationTool.vue'

export default {
  name: 'App',
  components: {
    MigrationTool
  },
  data() {
    return {
      loading: false
    }
  },
  methods: {
    loadData() {
      // This will be handled by the MigrationTool component
    }
  }
}
</script>