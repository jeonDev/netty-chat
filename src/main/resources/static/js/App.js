const { useState, useEffect, useRef } = React;

// --- HomePage Component ---
const HomePage = ({ onEnterChat }) => {
    const [nickname, setNickname] = useState('');

    const handleEnter = () => {
        if (nickname.trim()) {
            onEnterChat(nickname.trim());
        } else {
            alert('Please enter a nickname.');
        }
    };

    const handleKeyPress = (event) => {
        if (event.key === 'Enter') handleEnter();
    };

    return (
        <div style={styles.homeContainer}>
            <h2 style={{ marginBottom: '20px' }}>Welcome to the Chat</h2>
            <p style={{ marginBottom: '20px', color: '#555' }}>Please enter a nickname to join.</p>
            <input
                style={styles.input}
                type="text"
                placeholder="Your Nickname"
                value={nickname}
                onChange={(e) => setNickname(e.target.value)}
                onKeyPress={handleKeyPress}
            />
            <button style={styles.button} onClick={handleEnter}>Enter Chat Room</button>
        </div>
    );
};

// --- ChatPage Component ---
const ChatPage = ({ ws, nickname }) => {
    const [message, setMessage] = useState('');
    const [messages, setMessages] = useState([]);
    const messageAreaRef = useRef(null);

    useEffect(() => {
        if (!ws) return;

        ws.onmessage = (event) => {
            try {
                const data = JSON.parse(event.data);
                // Don't show messages sent by me that came back from the server
                if (data.sender !== nickname) {
                    setMessages(prev => [...prev, { ...data, type: 'user' }]);
                }
            } catch (e) {
                setMessages(prev => [...prev, { sender: 'Server', message: event.data, type: 'user' }]);
            }
        };

        ws.onopen = () => {
             setMessages(prev => [...prev, { type: 'system', message: 'Connected to the chat server.' }]);
        }

        ws.onclose = () => {
            setMessages(prev => [...prev, { type: 'system', message: 'Connection lost.' }]);
        }

    }, [ws, nickname]);

    useEffect(() => {
        if (messageAreaRef.current) {
            messageAreaRef.current.scrollTop = messageAreaRef.current.scrollHeight;
        }
    }, [messages]);

    const handleSendMessage = () => {
        if (message.trim() === "" || !ws || ws.readyState !== WebSocket.OPEN) return;
        
        const data = { sender: nickname, message: message };
        ws.send(JSON.stringify(data));
        setMessages(prev => [...prev, { ...data, type: 'user', isMine: true }]);
        setMessage('');
    };

    const handleKeyPress = (event) => {
        if (event.key === 'Enter') handleSendMessage();
    };

    return (
        <div>
            <h2 style={{textAlign: 'center'}}>Chat Room (Hi, {nickname})</h2>
            <div style={styles.messageArea} ref={messageAreaRef}>
                {messages.map((msg, index) => {
                    if (msg.type === 'system') {
                        return <div key={index} style={styles.systemMessage}>{msg.message}</div>;
                    }
                    const isMine = msg.isMine;
                    const messageStyle = isMine ? styles.myMessage : styles.otherMessage;
                    return (
                        <div key={index} style={messageStyle.container}>
                            <div style={messageStyle.content}>
                                {!isMine && <div style={styles.sender}>{msg.sender}</div>}
                                <span>{msg.message}</span>
                            </div>
                        </div>
                    );
                })}
            </div>
            <div style={styles.inputArea}>
                <input
                    style={styles.input}
                    type="text"
                    placeholder="Enter your message..."
                    value={message}
                    onChange={(e) => setMessage(e.target.value)}
                    onKeyPress={handleKeyPress}
                />
                <button style={styles.button} onClick={handleSendMessage}>Send</button>
            </div>
        </div>
    );
};


// --- Main App Component ---
const App = () => {
    const [page, setPage] = useState('home'); // 'home' or 'chat'
    const [nickname, setNickname] = useState('');
    const [ws, setWs] = useState(null);

    useEffect(() => {
        // Establish WebSocket connection once
        const webSocket = new WebSocket("ws://localhost:10005/ws");
        setWs(webSocket);

        // Cleanup on component unmount
        return () => {
            if (webSocket) {
                webSocket.close();
            }
        };
    }, []); // Empty array ensures this runs only once

    const handleEnterChat = (name) => {
        setNickname(name);
        setPage('chat');
    };

    return (
        <div>
            {page === 'home' ? (
                <HomePage onEnterChat={handleEnterChat} />
            ) : (
                <ChatPage ws={ws} nickname={nickname} />
            )}
        </div>
    );
};

// --- Styles ---
const styles = {
    homeContainer: {
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
        justifyContent: 'center',
        padding: '40px',
        border: '1px solid #ccc',
        borderRadius: '8px',
        maxWidth: '400px',
        margin: '50px auto',
        backgroundColor: '#f9f9f9'
    },
    input: { padding: '10px', fontSize: '16px', width: '80%', borderRadius: '4px', border: '1px solid #ddd' },
    button: { padding: '10px 20px', fontSize: '16px', cursor: 'pointer', backgroundColor: '#007bff', color: 'white', border: 'none', borderRadius: '4px' },
    messageArea: { border: '1px solid #ccc', padding: '10px', height: '400px', overflowY: 'scroll', marginBottom: '10px', backgroundColor: '#fafafa', borderRadius: '4px' },
    inputArea: { display: 'flex', gap: '10px' },
    systemMessage: { fontStyle: 'italic', color: '#888', textAlign: 'center', width: '100%', marginBottom: '8px' },
    sender: { fontWeight: 'bold', marginBottom: '4px', fontSize: '0.9em', color: '#555' },
    myMessage: {
        container: { textAlign: 'right', marginBottom: '8px' },
        content: { backgroundColor: '#dcf8c6', padding: '8px 12px', borderRadius: '15px', display: 'inline-block', textAlign: 'left' }
    },
    otherMessage: {
        container: { textAlign: 'left', marginBottom: '8px' },
        content: { backgroundColor: '#f1f1f1', padding: '8px 12px', borderRadius: '15px', display: 'inline-block' }
    }
};

ReactDOM.render(<App />, document.getElementById('root'));
