import axios from "axios";

const fileInstance = axios.create({
  baseURL: `/api/files`,
  headers: {authorization: localStorage.getItem("accessToken")},
});

const filesApi = {
  upload(file: File) {
    const formData = new FormData();

    formData.append("files", file);
    return fileInstance.post("/", formData, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    });
  },
};

export default filesApi;
