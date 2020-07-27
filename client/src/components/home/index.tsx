import React from "react";
import {Link} from "react-router-dom";
import * as S from "./assets";
import Layout from "../common/Layout";
import {MAX_WIDTH, PRIMARY_GREEN, SECONDARY_GREEN} from "../../domains/constants";

const Home: React.FC = () => (
  <Layout>
    <S.Home>
      <S.Section background={PRIMARY_GREEN}>
        <S.Layout>
          <S.Slogan>Beautiful,</S.Slogan>
          <S.Slogan>Minimal <span style={{color: "#00FF7F"}}>Slides</span></S.Slogan>
          <S.Slogan>With Markdown</S.Slogan>
          <Link to="/me">
            <S.Button>Get Started for Free →</S.Button>
          </Link>
        </S.Layout>
      </S.Section>
      <S.Section background="#333">
        <S.Layout>
          <code style={{color: "#fff"}}>
            --- <br/>
            title: Hello Limetree! <br/>
            author: Hodol <br/>
            created_at: 2020-07-12 <br/>
            --- <br/> <br/>
            ## Works like charm <br/>
            ### with minimal effort <br/> <br/>
            - Only need to type <br/>
            - Supports GFM Markdown <br/>
            - Youtube, Charts, and more! <br/> <br/>
            --- <br/> <br/>
            ## Focus on your idea <br/> <br/>
            > No more decorating stuff <br/>
            > Pixel perfect beautiful slides
          </code>
        </S.Layout>
      </S.Section>
      <S.Section background={PRIMARY_GREEN}>
        <div style={{
          width: "100%",
          maxWidth: MAX_WIDTH,
          margin: "0 auto",
          minHeight: "30rem",
          backgroundColor: "#fff",
          fontWeight: "bold",
          fontSize: 20,
          display: "flex",
          justifyContent: "center",
          alignItems: "center",
          boxShadow: "1px 1px 1px #aaa",
        }}>
            Slide example goes here
        </div>
      </S.Section>
    </S.Home>
  </Layout>
);

export default Home;
