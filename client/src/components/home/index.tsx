import React from "react";
import Layout from "../common/Layout";
import FullScreenMode from "../common/FullScreenMode";
import {sampleMarkdown} from "../../utils/fixtures";

const Home: React.FC = () => (
  <Layout>
    <h1>Home</h1>

    {/* FullScreenMode Sample*/}
    <FullScreenMode
      content={sampleMarkdown.content}
      delimiter={sampleMarkdown.delimiter}
    />

  </Layout>
);

export default Home;
