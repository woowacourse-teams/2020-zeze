import React from "react";
import {User} from "../../pages/Me";


const Info: React.FC<User> = ({user}: User, updateInfo) => (
    <div>
      <h2>{user.name}'s Info</h2>
      <hr/>
      <input></input>
      <input></input>
      <button onClick={updateInfo}></button>
    </div>
);
export default Info
