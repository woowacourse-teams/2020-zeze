import React from "react";
import styled from "@emotion/styled";

import EditorButton from "./EditorButton";
import menu from "../../assets/icons/hamburger.svg";
import heading from "../../assets/icons/editor/heading.svg";
import image from "../../assets/icons/editor/image.svg";
import link from "../../assets/icons/editor/link.svg";
import bullets from "../../assets/icons/editor/bullets.svg";
import list from "../../assets/icons/editor/list.svg";
import table from "../../assets/icons/editor/table.svg";
import quote from "../../assets/icons/editor/quote.svg";
import chart from "../../assets/icons/editor/chart.svg";
import code from "../../assets/icons/editor/code.svg";
import page from "../../assets/icons/editor/page.svg";
import youtube from "../../assets/icons/editor/youtube.svg";
import LastModified from "./LastModified";
import {sidebarVisibility} from "../../store/atoms";
import {useRecoilState, useSetRecoilState} from "recoil";


const Menu = styled.div`
  padding: 5px;
  cursor: pointer;
  margin-right: 15px;
  > img {
    width: 20px;
  }
`;

const EditorButtonsBlock = styled.div`
  height: 50px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 20px;
  background-color: #313335;
  
  > div.container {
    height: 100%;
    display: flex;
    align-items: center;
    justify-content: space-between;
    overflow-x: scroll;
  }
`;

const EditorButtons: React.FC = () => {
  const setVisibility = useSetRecoilState(sidebarVisibility);
  const buttonData = [
    {
      title: "Heading",
      src: heading,
      template: "# \n",
    }, {
      title: "Image",
      src: image,
      template: "# \n",
    }, {
      title: "Link",
      src: link,
      template: "# \n",
    }, {
      title: "Bullets",
      src: bullets,
      template: "# \n",
    }, {
      title: "List",
      src: list,
      template: "# \n",
    }, {
      title: "Table",
      src: table,
      template: "# \n",
    }, {
      title: "Quote",
      src: quote,
      template: "# \n",
    }, {
      title: "Youtube",
      src: youtube,
      template: "# \n",
    }, {
      title: "Chart",
      src: chart,
      template: "# \n",
    }, {
      title: "Code",
      src: code,
      template: "# \n",
    }, {
      title: "Page",
      src: page,
      template: "# \n",
    },
  ];

  return (
    <EditorButtonsBlock>
      <Menu onClick={() => setVisibility(true)}><img src={menu} alt={menu}/></Menu>
      <div className="container">{buttonData.map(data => <EditorButton {...data}/>)}</div>
      <LastModified at={new Date().toLocaleTimeString()}></LastModified>
    </EditorButtonsBlock>
  );
};

export default React.memo(EditorButtons);
