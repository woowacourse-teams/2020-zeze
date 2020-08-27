import axios from "axios";

const fileInstance = axios.create({
  baseURL: `/api/files`,
});

const filesApi = {
  upload(file: File) {
    const formData = new FormData();

    formData.append("files", file);
    return fileInstance.post("/", formData, {
      headers: {
        "Content-Type": "multipart/form-data",
        authorization: localStorage.getItem("accessToken"),
      },
    });
  },
  uploadExternal(fileUrl: string, fileName: string) {
    return fileInstance.post("/external", {
      fileUrl,
      fileName,
    }, {
      headers: {
        authorization: localStorage.getItem("accessToken"),
      },
    });
  },
};

export default filesApi;
