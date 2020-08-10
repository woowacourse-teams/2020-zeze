import React, {useEffect} from "react";
import axios from "axios";
import GlobalLayout from "../components/common/GlobalLayout";
import Spinner from "../components/common/Spinner";

interface props {
  location: string,
  history: string[]
}

const Callback: React.FC<props> = ({location, history}: props) => {
  const githubBaseUrl = "/api/signin/github";

  useEffect(() => {
    const getToken = async () => {
      const code: string | null = new URLSearchParams(location.search.toString()).get("code");
      const accessToken: string = (await axios.post(githubBaseUrl, {
        provider: "GITHUB",
        code: code? code : ""
      })).data.accessToken;

      localStorage.setItem("accessToken", `bearer ${accessToken}`);
    };

    getToken()
      .then(() => history.push("/me"))
      .catch(() => {
        alert("login failed");
        history.push("/");
      });
  }, [location, history]);

  return (
    <GlobalLayout>
      <Spinner/>
    </GlobalLayout>
  );
};

export default Callback;
