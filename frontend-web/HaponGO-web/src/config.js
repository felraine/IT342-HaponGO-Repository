const API_BASE_URL = process.env.NODE_ENV === 'production'
  ? 'https://hapongo-backend-819908927275.asia-southeast1.run.app'
  : 'http://localhost:8080';

export default API_BASE_URL;