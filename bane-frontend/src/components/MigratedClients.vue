<template>
  <div>
    <v-card>
      <v-card-title>
        Migrated Clients ({{ clients.length }})
      </v-card-title>

      <v-card-text>
        <v-progress-linear v-if="loading" indeterminate></v-progress-linear>

        <v-table v-else>
          <thead>
          <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Email</th>
            <th>Company</th>
            <th>Status</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="client in clients" :key="client.id">
            <td>{{ client.id }}</td>
            <td>{{ client.name }}</td>
            <td>{{ client.email }}</td>
            <td>{{ client.company }}</td>
            <td>
              <v-chip color="success" size="small">Migrated</v-chip>
            </td>
          </tr>
          <tr v-if="clients.length === 0">
            <td colspan="5" class="text-center">No migrated clients yet</td>
          </tr>
          </tbody>
        </v-table>
      </v-card-text>
    </v-card>
  </div>
</template>

<script>
export default {
  name: 'MigratedClients',
  props: {
    clients: {
      type: Array,
      required: true
    },
    loading: {
      type: Boolean,
      default: false
    }
  }
}
</script>