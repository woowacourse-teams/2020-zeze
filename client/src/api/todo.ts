import axios, {AxiosResponse} from "axios";

// This is an example of Axios Request.

interface Todo {
  userId: number,
  id: number,
  title: string,
  completed: boolean
}

export default async (id: number): Promise<AxiosResponse<Todo>> => {
  const response = await axios.get("https://jsonplaceholder.typicode.com/todos/1");

  return response.data;
};
