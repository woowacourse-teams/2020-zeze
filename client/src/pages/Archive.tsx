import React, {useEffect, useState} from "react";
import SidebarLayout from "../components/common/SidebarLayout";
import Cards from "../components/common/Cards";
import Toast from "../components/common/Toast";
import slideApi, {SlideResponse} from "../api/slide";

const Archive: React.FC = () => {
  const [slides, setSlides] = useState<Array<SlideResponse>>([]);

  useEffect(() => {
    slideApi.getAll({id: 0, size: 5})
      .then(res => setSlides(res.data.slides));
  }, []);

  return (
    <SidebarLayout>
      <Toast type="warn" message="Currently in development. Sorry for your inconvenience :("/>
      <Cards title="Archive" slides={slides}/>
    </SidebarLayout>
  );
};

export default Archive;
