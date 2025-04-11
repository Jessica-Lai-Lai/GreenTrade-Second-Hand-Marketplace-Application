import React from "react";
import ReactDOM from "react-dom";

import "./styles.css";

class App extends React.Component {
  componentDidMount() {
    const domain = "im.enssi.cn";
    const options = {
      width: 1280,
      height: 900,
      parentNode: document.querySelector("#meet"),
      interfaceConfigOverwrite: {
        DEFAULT_BACKGROUND: "#5baaff",
        SHOW_JITSI_WATERMARK: false,
        noSsl: true,
        JITSI_WATERMARK_LINK: "#",
        SHOW_BRAND_WATERMARK: false,
        SHOW_WATERMARK_FOR_GUESTS: false,
        SHOW_JITSI_WATERMARK: false,
        SHOW_POWERED_BY: false,
        TOOLBAR_BUTTONS: ["invite"],
      },
    };
    const api = new window.JitsiMeetExternalAPI(domain, options);
  }
  render() {
    return (
      <div className="App">
        <div id="meet" />
      </div>
    );
  }
}

const rootElement = document.getElementById("root");
ReactDOM.render(<App />, rootElement);
