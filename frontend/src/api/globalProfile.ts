import axios from 'axios';

const baseURL = 'http://localhost:8080';

export const globalProfileAPI = {
  checkProfile: async () => {
    const token = localStorage.getItem('token');
    const response = await axios.get(`${baseURL}/api/global-profile/check`, {
      headers: {
        Authorization: `Bearer ${token}`
      }
    });
    return response.data;
  },

  getProfile: async () => {
    const token = localStorage.getItem('token');
    const response = await axios.get(`${baseURL}/api/global-profile`, {
      headers: {
        Authorization: `Bearer ${token}`
      }
    });
    return response.data;
  },

  saveProfile: async (profileData) => {
    const token = localStorage.getItem('token');
    const response = await axios.post(`${baseURL}/api/global-profile`, profileData, {
      headers: {
        Authorization: `Bearer ${token}`
      }
    });
    return response.data;
  },

  updateProfile: async (profileData) => {
    const token = localStorage.getItem('token');
    const response = await axios.put(`${baseURL}/api/global-profile`, profileData, {
      headers: {
        Authorization: `Bearer ${token}`
      }
    });
    return response.data;
  }
};
