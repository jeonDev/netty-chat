const { useState, useEffect, useRef } = React;
const { BrowserRouter, Route, Switch, Redirect, Link } = ReactRouterDOM;

// --- MainPage Component ---
const MainPage = () => {
    return (
        <div style={styles.homeContainer}>
            <h1 style={{ marginBottom: '30px' }}>Welcome to Chat App</h1>
            <Link to="/login">
                <button style={styles.button}>Go to Login</button>
            </Link>
        </div>
    );
};


// --- LoginPage Component ---
const LoginPage = ({ onLogin }) => {
    const [id, setId] = useState('');
    const [password, setPassword] = useState('');
    const history = ReactRouterDOM.useHistory();

    const handleLogin = () => {
        if (!id.trim() || !password.trim()) {
            alert('아이디와 비밀번호를 모두 입력해주세요.');
            return;
        }

        fetch('/api/v1/login', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ loginId: id, password: password })
        })
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error('로그인에 실패했습니다. 아이디와 비밀번호를 확인해주세요.');
            }
        })
        .then(data => {
            localStorage.setItem('accessToken', data.accessToken);
            localStorage.setItem('refreshToken', data.refreshToken);
            localStorage.setItem('grantType', data.grantType);
            localStorage.setItem('nickname', data.nickName);
            onLogin(data.nickName);
            history.push('/chat');
        })
        .catch(error => {
            console.error('Login API error:', error);
            alert(error.message);
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
                <Link to="/signup">
                    <button style={{...styles.button, ...styles.secondaryButton}}>Sign Up</button>
                </Link>
            </div>
        </div>
    );
};


// --- SignUpPage Component ---
const SignUpPage = () => {
    const [formData, setFormData] = useState({
        loginId: '',
        name: '',
        nickName: '',
        phoneNumber: '',
        password: ''
    });
    const history = ReactRouterDOM.useHistory();

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData(prev => ({ ...prev, [name]: value }));
    };

    const handleSignUp = () => {
        const { loginId, name, nickName, phoneNumber, password } = formData;
        if (!loginId || !name || !nickName || !phoneNumber || !password) {
            alert('모든 필드를 입력해주세요.');
            return;
        }
        if (password.length < 8 || password.length > 20) {
            alert('비밀번호는 8자 이상 20자 이하로 입력해주세요.');
            return;
        }

        fetch('/api/v1/member', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(formData)
        })
        .then(response => {
            if (response.ok) {
                alert('회원가입 성공! 로그인 해주세요.');
                history.push('/login');
            } else {
                alert('회원가입 실패. 다시 시도해주세요.');
            }
        })
        .catch(error => {
            console.error('Sign-up API error:', error);
            alert('회원가입 중 오류가 발생했습니다.');
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
                 <Link to="/login">
                    <button style={{...styles.button, ...styles.secondaryButton}}>Back to Login</button>
                 </Link>
            </div>
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
                setMessages(prev => [...prev, { ...data, type: 'user' }]);
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
        console.log('ws.readyState:', ws.readyState);
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
                            {!isMine && <div style={styles.sender}>{msg.sender}</div>}
                            <div style={messageStyle.content}>
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
    const [nickname, setNickname] = useState(localStorage.getItem('nickname') || '');
    const [ws, setWs] = useState(null);

    useEffect(() => {
        const webSocket = new WebSocket("ws://localhost:10005/ws");
        setWs(webSocket);
        return () => { if (webSocket) webSocket.close(); };
    }, []);

    return (
        <BrowserRouter>
            <Switch>
                <Route path="/login">
                    <LoginPage onLogin={setNickname} />
                </Route>
                <Route path="/signup">
                    <SignUpPage />
                </Route>
                <Route path="/chat">
                    {nickname ? <ChatPage ws={ws} nickname={nickname} /> : <Redirect to="/login" />}
                </Route>
                <Route path="/">
                    <MainPage />
                </Route>
            </Switch>
        </BrowserRouter>
    );
};

// --- Styles ---
const styles = {
    homeContainer: { display: 'flex', flexDirection: 'column', alignItems: 'center', justifyContent: 'center', padding: '40px', border: '1px solid #ccc', borderRadius: '8px', maxWidth: '400px', margin: '50px auto', backgroundColor: '#f9f9f9' },
    input: { padding: '10px', fontSize: '16px', width: '80%', borderRadius: '4px', border: '1px solid #ddd' },
    button: { padding: '10px 20px', fontSize: '16px', cursor: 'pointer', backgroundColor: '#FFEB00', color: 'black', border: 'none', borderRadius: '4px' },
    buttonGroup: { display: 'flex', gap: '10px' },
    secondaryButton: { backgroundColor: '#f2f2f2', color: 'black' },
    messageArea: { backgroundColor: '#b2c7d9', padding: '10px', height: '400px', overflowY: 'scroll', marginBottom: '10px', borderRadius: '4px' },
    inputArea: { display: 'flex', gap: '10px', padding: '10px', backgroundColor: '#f2f2f2' },
    systemMessage: { fontStyle: 'italic', color: '#888', textAlign: 'center', width: '100%', marginBottom: '8px' },
    sender: { fontWeight: 'bold', marginBottom: '5px', fontSize: '0.9em', color: '#555' },
    myMessage: { container: { textAlign: 'right', marginBottom: '8px' }, content: { backgroundColor: '#FFEB00', padding: '8px 12px', borderRadius: '15px', display: 'inline-block', textAlign: 'left', maxWidth: '70%' } },
    otherMessage: { container: { textAlign: 'left', marginBottom: '8px' }, content: { backgroundColor: '#ffffff', padding: '8px 12px', borderRadius: '15px', display: 'inline-block', maxWidth: '70%' } }
};

ReactDOM.render(<App />, document.getElementById('root'));