<template>
  <div>
    <!-- Connection Status Banner -->
    <v-alert v-if="connectionError" type="error" class="mb-4">
      <strong>Backend Connection Error</strong><br>
      {{ connectionError }}
      <div class="mt-2">
        <v-btn color="white" variant="flat" size="small" @click="loadData">
          Retry Connection
        </v-btn>
      </div>
    </v-alert>

    <!-- Tabs for switching between views -->
    <v-tabs v-model="currentTab" color="primary" class="mb-4">
      <v-tab value="legacy">
        <v-icon icon="mdi-account-multiple" class="mr-2"></v-icon>
        Legacy Clients
        <v-badge v-if="legacyClients.length > 0" :content="legacyClients.length" color="red" class="ml-2"></v-badge>
      </v-tab>
      <v-tab value="migrated">
        <v-icon icon="mdi-account-check" class="mr-2"></v-icon>
        Migrated Clients
        <v-badge v-if="migratedClients.length > 0" :content="migratedClients.length" color="green" class="ml-2"></v-badge>
      </v-tab>
    </v-tabs>

    <v-window v-model="currentTab">
      <!-- Legacy Clients Tab -->
      <v-window-item value="legacy">
        <LegacyClients
            :clients="legacyClients"
            :loading="loading"
            @migrate-client="migrateClient"
        />
      </v-window-item>

      <!-- Migrated Clients Tab -->
      <v-window-item value="migrated">
        <MigratedClients
            :clients="migratedClients"
            :loading="loading"
        />
      </v-window-item>
    </v-window>

    <!-- Snackbar for notifications -->
    <v-snackbar v-model="snackbar.show" :color="snackbar.color" timeout="4000">
      <div class="d-flex align-center">
        <v-icon :icon="snackbar.icon" class="mr-2"></v-icon>
        {{ snackbar.message }}
      </div>
      <template v-slot:actions>
        <v-btn variant="text" @click="snackbar.show = false">Close</v-btn>
      </template>
    </v-snackbar>
  </div>
</template>

<script>
import api from '../services/api'
import LegacyClients from './LegacyClients.vue'
import MigratedClients from './MigratedClients.vue'

export default {
  name: 'MigrationTool',
  components: {
    LegacyClients,
    MigratedClients
  },
  data() {
    return {
      currentTab: 'legacy',
      legacyClients: [],
      migratedClients: [],
      loading: false,
      connectionError: '',
      snackbar: {
        show: false,
        message: '',
        color: 'success',
        icon: 'mdi-check-circle'
      }
    }
  },
  methods: {
    // Load all data from backend
    async loadData() {
      this.loading = true
      this.connectionError = ''

      try {
        console.log('Loading data from backend...')

        const [legacyResult, newResult] = await Promise.all([
          api.getLegacyClients(),
          api.getNewClients()
        ])

        console.log('Legacy clients response:', legacyResult)
        console.log('New clients response:', newResult)

        this.legacyClients = legacyResult.data || []
        this.migratedClients = newResult.data || []

        this.showSnackbar('Data loaded successfully!', 'success', 'mdi-check-circle')

      } catch (error) {
        console.error('Error loading data:', error)

        let errorMessage = 'Error loading data from server'

        if (error.code === 'ECONNREFUSED') {
          errorMessage = 'Cannot connect to backend server. Please make sure: 1) Spring Boot is running on http://localhost:8080, 2) CORS is configured properly'
        } else if (error.response) {
          errorMessage = `Server error: ${error.response.data?.message || error.response.status}`
        } else if (error.request) {
          errorMessage = 'No response from server. The backend might not be running or CORS is blocking the request.'
        } else {
          errorMessage = `Network error: ${error.message}`
        }

        this.connectionError = errorMessage
        this.showSnackbar(errorMessage, 'error', 'mdi-alert-circle')

        // Set empty arrays to clear any old data
        this.legacyClients = []
        this.migratedClients = []
      } finally {
        this.loading = false
      }
    },

    // Migrate a client
    async migrateClient(clientId) {
      this.loading = true
      try {
        console.log(`Migrating client ${clientId}...`)

        const result = await api.migrateClient(clientId)

        if (result.success) {
          this.showSnackbar(result.message || 'Client migrated successfully!', 'success', 'mdi-check-circle')
          // Reload data to get updated lists
          await this.loadData()
        } else {
          this.showSnackbar(result.message || 'Migration failed', 'error', 'mdi-alert-circle')
        }
      } catch (error) {
        console.error('Migration error:', error)
        let errorMessage = 'Migration failed'

        if (error.response) {
          errorMessage = error.response.data?.message || `Server error: ${error.response.status}`
        } else if (error.request) {
          errorMessage = 'No response from server. Check backend connection.'
        } else {
          errorMessage = `Error: ${error.message}`
        }

        this.showSnackbar(errorMessage, 'error', 'mdi-alert-circle')
      } finally {
        this.loading = false
      }
    },

    // Helper to show notifications
    showSnackbar(message, color = 'success', icon = 'mdi-check-circle') {
      this.snackbar.message = message
      this.snackbar.color = color
      this.snackbar.icon = icon
      this.snackbar.show = true
    }
  },

  // Load data when component is created
  created() {
    this.loadData()
  }
}
</script>