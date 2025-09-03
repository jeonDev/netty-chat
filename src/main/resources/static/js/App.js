const { useState, useEffect, useRef } = React;

// --- MainPage Component ---
const MainPage = ({ onNavigateToLogin }) => (
    <div style={styles.homeContainer}>
        <h1 style={{ marginBottom: '30px' }}>Welcome to Chat App</h1>
        <button style={styles.button} onClick={onNavigateToLogin}>Go to Login</button>
    </div>
);


// --- LoginPage Component ---
const LoginPage = ({ onLogin, onNavigateToSignUp }) => {
    const [id, setId] = useState('');
    const [password, setPassword] = useState('');

    const handleLogin = () => {
        if (!id.trim() || !password.trim()) {
            alert('Please enter both ID and Password.');
            return;
        }

        fetch('/api/v1/login', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ loginId: id, password: password })
        })
        .then(response => {
            if (response.ok) {
                // Per user request, proceed to chat on successful request.
                // Later, we will handle the response data (e.g., tokens).
                onLogin(id.trim());
            } else {
                // Handle login failure
                alert('Login failed. Please check your credentials.');
            }
        })
        .catch(error => {
            console.error('Login API error:', error);
            alert('An error occurred during login.');
        });
    };

    const handleKeyPress = (event) => {
        if (event.key === 'Enter') handleLogin();
    };

    return (
        <div style={styles.homeContainer}>
            <h2 style={{ marginBottom: '20px' }}>Login</h2>
            <input style={styles.input} type="text" placeholder="ID" value={id} onChange={(e) => setId(e.target.value)} onKeyPress={handleKeyPress} />
            <input style={{...styles.input, marginTop: '10px'}} type="password" placeholder="Password" value={password} onChange={(e) => setPassword(e.target.value)} onKeyPress={handleKeyPress} />
            <div style={{...styles.buttonGroup, marginTop: '20px'}}>
                <button style={styles.button} onClick={handleLogin}>Login</button>
                <button style={{...styles.button, ...styles.secondaryButton}} onClick={onNavigateToSignUp}>Sign Up</button>
            </div>
        </div>
    );
};


// --- SignUpPage Component ---
const SignUpPage = ({ onNavigateToLogin }) => {
    const [formData, setFormData] = useState({
        loginId: '',
        name: '',
        nickName: '',
        phoneNumber: '',
        password: ''
    });

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData(prev => ({ ...prev, [name]: value }));
    };

    const handleSignUp = () => {
        const { loginId, name, nickName, phoneNumber, password } = formData;
        if (!loginId || !name || !nickName || !phoneNumber || !password) {
            alert('Please fill out all fields.');
            return;
        }
        if (password.length < 8 || password.length > 20) {
            alert('Password must be between 8 and 20 characters.');
            return;
        }

        fetch('/api/v1/member', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(formData)
        })
        .then(response => {
            if (response.ok) {
                alert('Sign-up successful! Please log in.');
                onNavigateToLogin();
            } else {
                alert('Sign-up failed. Please try again.');
            }
        })
        .catch(error => {
            console.error('Sign-up API error:', error);
            alert('An error occurred during sign-up.');
        });
    };

    return (
        <div style={styles.homeContainer}>
            <h2 style={{ marginBottom: '20px' }}>Sign Up</h2>
            <input style={styles.input} name="loginId" placeholder="Login ID" value={formData.loginId} onChange={handleChange} />
            <input style={{...styles.input, marginTop: '10px'}} name="password" type="password" placeholder="Password (8-20 characters)" value={formData.password} onChange={handleChange} />
            <input style={{...styles.input, marginTop: '10px'}} name="name" placeholder="Name" value={formData.name} onChange={handleChange} />
            <input style={{...styles.input, marginTop: '10px'}} name="nickName" placeholder="Nickname" value={formData.nickName} onChange={handleChange} />
            <input style={{...styles.input, marginTop: '10px'}} name="phoneNumber" placeholder="Phone Number" value={formData.phoneNumber} onChange={handleChange} />
            <div style={{...styles.buttonGroup, marginTop: '20px'}}>
                 <button style={styles.button} onClick={handleSignUp}>Sign Up</button>
                 <button style={{...styles.button, ...styles.secondaryButton}} onClick={onNavigateToLogin}>Back to Login</button>
            </div>
        </div>
    );
};


// --- ChatPage Component (remains the same) ---
const ChatPage = ({ ws, nickname }) => {
    const [message, setMessage] = useState('');
    const [messages, setMessages] = useState([]);
    const messageAreaRef = useRef(null);

    useEffect(() => {
        if (!ws) return;
        ws.onmessage = (event) => {
            try {
                const data = JSON.parse(event.data);
                if (data.sender !== nickname) setMessages(prev => [...prev, { ...data, type: 'user' }]);
            } catch (e) {
                setMessages(prev => [...prev, { sender: 'Server', message: event.data, type: 'user' }]);
            }
        };
        ws.onopen = () => setMessages(prev => [...prev, { type: 'system', message: 'Connected to the chat server.' }]);
        ws.onclose = () => setMessages(prev => [...prev, { type: 'system', message: 'Connection lost.' }]);
    }, [ws, nickname]);

    useEffect(() => {
        if (messageAreaRef.current) messageAreaRef.current.scrollTop = messageAreaRef.current.scrollHeight;
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
                    if (msg.type === 'system') return <div key={index} style={styles.systemMessage}>{msg.message}</div>;
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
                <input style={{...styles.input, width: '100%'}} type="text" placeholder="Enter your message..." value={message} onChange={(e) => setMessage(e.target.value)} onKeyPress={handleKeyPress} />
                <button style={styles.button} onClick={handleSendMessage}>Send</button>
            </div>
        </div>
    );
};


// --- Main App Component ---
const App = () => {
    const [page, setPage] = useState('main'); // 'main', 'login', 'signup', or 'chat'
    const [nickname, setNickname] = useState('');
    const [ws, setWs] = useState(null);

    useEffect(() => {
        const webSocket = new WebSocket("ws://localhost:10005/ws");
        setWs(webSocket);
        return () => { if (webSocket) webSocket.close(); };
    }, []);

    const renderPage = () => {
        switch (page) {
            case 'login':
                return <LoginPage onLogin={(id) => { setNickname(id); setPage('chat'); }} onNavigateToSignUp={() => setPage('signup')} />;
            case 'signup':
                return <SignUpPage onNavigateToLogin={() => setPage('login')} />;
            case 'chat':
                return <ChatPage ws={ws} nickname={nickname} />;
            case 'main':
            default:
                return <MainPage onNavigateToLogin={() => setPage('login')} />;
        }
    };

    return <div>{renderPage()}</div>;
};

// --- Styles ---
const styles = {
    homeContainer: { display: 'flex', flexDirection: 'column', alignItems: 'center', justifyContent: 'center', padding: '40px', border: '1px solid #ccc', borderRadius: '8px', maxWidth: '400px', margin: '50px auto', backgroundColor: '#f9f9f9' },
    input: { padding: '10px', fontSize: '16px', width: '80%', borderRadius: '4px', border: '1px solid #ddd' },
    button: { padding: '10px 20px', fontSize: '16px', cursor: 'pointer', backgroundColor: '#007bff', color: 'white', border: 'none', borderRadius: '4px' },
    buttonGroup: { display: 'flex', gap: '10px' },
    secondaryButton: { backgroundColor: '#6c757d' },
    messageArea: { border: '1px solid #ccc', padding: '10px', height: '400px', overflowY: 'scroll', marginBottom: '10px', backgroundColor: '#fafafa', borderRadius: '4px' },
    inputArea: { display: 'flex', gap: '10px' },
    systemMessage: { fontStyle: 'italic', color: '#888', textAlign: 'center', width: '100%', marginBottom: '8px' },
    sender: { fontWeight: 'bold', marginBottom: '4px', fontSize: '0.9em', color: '#555' },
    myMessage: { container: { textAlign: 'right', marginBottom: '8px' }, content: { backgroundColor: '#dcf8c6', padding: '8px 12px', borderRadius: '15px', display: 'inline-block', textAlign: 'left' } },
    otherMessage: { container: { textAlign: 'left', marginBottom: '8px' }, content: { backgroundColor: '#f1f1f1', padding: '8px 12px', borderRadius: '15px', display: 'inline-block' } }
};

ReactDOM.render(<App />, document.getElementById('root'));
