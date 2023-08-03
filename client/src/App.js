import { useEffect, useState } from "react";
import "./App.css";

function App() {
  const [user, setUser] = useState("");
  const [events, setEvents] = useState([]);
  

  useEffect(() => {
    const sseForLastMessage = new EventSource(
      `http://127.0.0.1:8080/api/v1/sse/user/messages?name=${user}`
    );

    sseForLastMessage.onopen = (e) => {
      console.log("SSE For LastMessage Connected !");
    };

    sseForLastMessage.addEventListener("notification-message-event", (event) => {
      let jsonData = JSON.parse(event.data);
      console.log(jsonData);
      setEvents([...events, jsonData])
    });

    sseForLastMessage.onerror = (error) => {
      console.log("SSE For LastMessage Error", error);
      sseForLastMessage.close();
    };
    
    return () => {
      sseForLastMessage.close();
    };

  }, [user, events]);

  const handleSubmit = () => {
    setUser("user-1")
  };

  return (
    <div className="App">
      <button onClick={handleSubmit}>Event Streaming</button>

      {events.length}

      {events.map(e => (  <div key={e.id} >{e.id + " " + e.message}</div> ) )}
    </div>
  );
}

export default App;
