const { useState, useEffect, useRef } = React;
const { useLocation, useHistory } = ReactRouterDOM;

const ChatPage = () => {
    const location = useLocation();
    const history = useHistory();
    
    const [nickname, setNickname] = useState(location.state?.nickname || '');
    const [message, setMessage] = useState('');
    const [messages, setMessages] = useState([]);
    const ws = useRef(null);
    const messageAreaRef = useRef(null);

    useEffect(() => {
        if (!nickname) {
            history.replace('/'); // If no nickname, redirect to home
            return;
        }

        const connect = () => {
            ws.current = new WebSocket("ws://localhost:10005/ws");

            ws.current.onopen = () => addMessage({ sender: 'System', message: 'Connected to the chat server.', type: 'system' });
            ws.current.onmessage = (event) => {
                try {
                    const data = JSON.parse(event.data);
                    if (data.sender !== nickname) { // Only show messages from others
                        addMessage({ ...data, type: 'user' });
                    }
                } catch (e) {
                    addMessage({ sender: 'Server', message: event.data, type: 'user' });
                }
            };
            ws.current.onclose = () => {
                addMessage({ sender: 'System', message: 'Disconnected. Trying to reconnect...', type: 'system' });
                setTimeout(connect, 3000);
            };
            ws.current.onerror = (error) => {
                addMessage({ sender: 'System', message: 'An error occurred.', type: 'system' });
                console.error("WebSocket Error: ", error);
            };
        };

        connect();

        return () => {
            if (ws.current) ws.current.close();
        };
    }, [nickname, history]);

    useEffect(() => {
        if (messageAreaRef.current) {
            messageAreaRef.current.scrollTop = messageAreaRef.current.scrollHeight;
        }
    }, [messages]);

    const addMessage = (msg) => {
        setMessages(prev => [...prev, msg]);
    };

    const sendMessage = () => {
        if (message.trim() === "") return;

        const data = { sender: nickname, message: message };

        if (ws.current && ws.current.readyState === WebSocket.OPEN) {
            ws.current.send(JSON.stringify(data));
            addMessage({ ...data, type: 'user', isMine: true });
            setMessage('');
        } else {
            addMessage({ sender: 'System', message: 'Connection is not open.', type: 'system' });
        }
    };

    const handleKeyPress = (event) => {
        if (event.key === 'Enter') sendMessage();
    };

    if (!nickname) return null; // Don't render if there's no nickname

    return (
        <div>
            <h2>Chat Room (Hi, {nickname})</h2>
            <div className="message-area" ref={messageAreaRef}>
                {messages.map((msg, index) => {
                    if (msg.type === 'system') {
                        return <div key={index} className="message system-message">{msg.message}</div>;
                    }
                    const messageClass = msg.isMine ? 'my-message' : 'other-message';
                    return (
                        <div key={index} className={`message ${messageClass}`}>
                            <div className="content">
                                {!msg.isMine && <span className="sender">{msg.sender}</span>}
                                <span>{msg.message}</span>
                            </div>
                        </div>
                    );
                })}
            </div>
            <div className="input-area">
                <input
                    type="text"
                    placeholder="Enter your message..."
                    value={message}
                    onChange={(e) => setMessage(e.target.value)}
                    onKeyPress={handleKeyPress}
                />
                <button onClick={sendMessage}>Send</button>
            </div>
        </div>
    );
};
