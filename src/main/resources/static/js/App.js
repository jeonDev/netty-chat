const { HashRouter, Route, Switch, Link } = ReactRouterDOM;
const { useState, useEffect, useRef, createContext, useContext } = React;

const WebSocketContext = createContext(null);

const App = () => {
    const [ws, setWs] = useState(null);
    const [globalMessages, setGlobalMessages] = useState([]); // To store messages globally if needed

    useEffect(() => {
        const connectWebSocket = () => {
            const newWs = new WebSocket("ws://localhost:10005/ws");

            newWs.onopen = () => {
                console.log("WebSocket Connected Globally.");
                // addGlobalMessage({ sender: 'System', message: 'Connected to the chat server.', type: 'system' });
            };

            newWs.onmessage = (event) => {
                try {
                    const data = JSON.parse(event.data);
                    // This is where you'd handle incoming messages globally if needed
                    // For now, ChatPage will handle its own display based on context
                    console.log("Global WS message received:", data);
                } catch (e) {
                    console.log("Global WS raw message received:", event.data);
                }
            };

            newWs.onclose = () => {
                console.log("WebSocket Disconnected Globally. Reconnecting...");
                // addGlobalMessage({ sender: 'System', message: 'Disconnected. Trying to reconnect...', type: 'system' });
                setTimeout(connectWebSocket, 3000); // Reconnect after 3 seconds
            };

            newWs.onerror = (error) => {
                console.error("Global WebSocket Error: ", error);
                // addGlobalMessage({ sender: 'System', message: 'An error occurred.', type: 'system' });
            };

            setWs(newWs);
        };

        connectWebSocket();

        return () => {
            if (ws) {
                ws.close();
            }
        };
    }, []); // Empty dependency array means this runs once on mount

    const sendGlobalMessage = (data) => {
        if (ws && ws.readyState === WebSocket.OPEN) {
            ws.send(JSON.stringify(data));
        } else {
            console.warn("WebSocket not open. Message not sent.", data);
        }
    };

    return (
        <HashRouter>
            <h1>React Chat SPA</h1>
            <nav style={{ marginBottom: '20px', textAlign: 'center' }}>
                <Link to="/" style={{ margin: '0 10px' }}>Home</Link>
                <Link to="/chat" style={{ margin: '0 10px' }}>Chat</Link>
            </nav>
            <WebSocketContext.Provider value={{ wsInstance: ws, send: sendGlobalMessage }}>
                <Switch>
                    <Route exact path="/" component={HomePage} />
                    <Route path="/chat" component={ChatPage} />
                </Switch>
            </WebSocketContext.Provider>
        </HashRouter>
    );
};