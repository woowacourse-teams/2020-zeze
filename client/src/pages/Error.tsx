import React, {useEffect} from "react";
import {googleAnalyticsException} from "../utils/googleAnalytics";

const Error: React.FC = () => {
  useEffect(() => {
    googleAnalyticsException("404 페이지 찾을 수 없음");
  }, []);

  return (
    <div>
      <h1>404 Page Not Found</h1>
    </div>
  );
};

export default Error;
