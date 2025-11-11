<template>
  <div>
    <v-card>
      <v-card-title class="d-flex align-center">
        <v-icon icon="mdi-account-multiple" class="mr-2"></v-icon>
        Legacy Clients ({{ clients.length }})
        <v-spacer></v-spacer>
        <v-chip color="orange" variant="outlined">
          Waiting for Migration
        </v-chip>
      </v-card-title>

      <v-card-text>
        <v-progress-linear v-if="loading" indeterminate color="primary"></v-progress-linear>

        <v-table v-else density="comfortable">
          <thead>
          <tr>
            <th class="font-weight-bold">ID</th>
            <th class="font-weight-bold">Name</th>
            <th class="font-weight-bold">Email</th>
            <th class="font-weight-bold">Company</th>
            <th class="font-weight-bold">Actions</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="client in clients" :key="client.id"
              :class="client.migrated ? 'bg-green-lighten-5' : ''">
            <td class="font-weight-medium">{{ client.id }}</td>
            <td>
              <div class="d-flex align-center">
                {{ client.name }}
                <v-icon v-if="client.migrated" color="success" icon="mdi-check-circle" class="ml-2" size="small"></v-icon>
              </div>
            </td>
            <td>{{ client.email }}</td>
            <td>
              <v-chip variant="outlined" size="small">
                {{ client.company }}
              </v-chip>
            </td>
            <td>
              <v-btn
                  color="primary"
                  variant="flat"
                  size="small"
                  @click="$emit('migrate-client', client.id)"
                  :disabled="client.migrated || loading"
                  :loading="loading"
                  prepend-icon="mdi-upload"
              >
                {{ client.migrated ? 'Already Migrated' : 'Migrate' }}
              </v-btn>
            </td>
          </tr>
          <tr v-if="clients.length === 0 && !loading">
            <td colspan="5" class="text-center py-8">
              <v-icon icon="mdi-information" size="large" class="mb-2" color="grey"></v-icon>
              <div class="text-grey">No legacy clients found</div>
            </td>
          </tr>
          </tbody>
        </v-table>
      </v-card-text>
    </v-card>
  </div>
</template>

<script>
export default {
  name: 'LegacyClients',
  props: {
    clients: {
      type: Array,
      required: true,
      default: () => []
    },
    loading: {
      type: Boolean,
      default: false
    }
  },
  emits: ['migrate-client']
}
</script>