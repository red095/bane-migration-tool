import axios from 'axios'

// Create axios instance with base configuration
const apiClient = axios.create({
    baseURL: 'http://localhost:8080/api',
    headers: {
        'Content-Type': 'application/json',
    },
    timeout: 5000,
})

// Add request interceptor for logging
apiClient.interceptors.request.use(
    (config) => {
        console.log(`Making ${config.method?.toUpperCase()} request to: ${config.url}`)
        return config
    },
    (error) => {
        console.error('Request error:', error)
        return Promise.reject(error)
    }
)

// Add response interceptor for logging
apiClient.interceptors.response.use(
    (response) => {
        console.log('Response received:', response.status, response.data)
        return response
    },
    (error) => {
        console.error('Response error:', error)
        if (error.code === 'ECONNREFUSED') {
            error.message = 'Cannot connect to backend server. Please make sure the Spring Boot application is running on http://localhost:8080'
        }
        return Promise.reject(error)
    }
)

// API methods
export default {
    async getLegacyClients() {
        try {
            const response = await apiClient.get('/legacy/clients')
            return response.data
        } catch (error) {
            console.error('Error fetching legacy clients:', error)
            throw error
        }
    },

    async getNewClients() {
        try {
            const response = await apiClient.get('/new/clients')
            return response.data
        } catch (error) {
            console.error('Error fetching new clients:', error)
            throw error
        }
    },

    async migrateClient(clientId) {
        try {
            const response = await apiClient.post(`/migrate/${clientId}`)
            return response.data
        } catch (error) {
            console.error('Error migrating client:', error)
            throw error
        }
    }
}