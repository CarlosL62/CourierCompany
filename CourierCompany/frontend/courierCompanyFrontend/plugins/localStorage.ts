export default defineNuxtPlugin(() => {
    return {
        provide: {
            locally: {
                getItem(item) {
                    if (process.client) {
                        return JSON.parse(localStorage.getItem(item)) 
                    } else {
                        return undefined
                    }
                },

                setItem(item, value) {
                    if (process.client) {
                        return localStorage.setItem(item, value)
                    }
                }
            }
        }
    }
})