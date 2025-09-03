const { useState } = React;

const HomePage = () => {
    const [nickname, setNickname] = useState('');
    const history = ReactRouterDOM.useHistory();

    const enterChat = () => {
        if (nickname.trim()) {
            history.push('/chat', { nickname: nickname.trim() });
        } else {
            alert('Please enter a nickname.');
        }
    };
    
    const handleKeyPress = (event) => {
        if (event.key === 'Enter') enterChat();
    };

    const containerStyle = {
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
    };

    const inputStyle = {
        padding: '10px',
        fontSize: '16px',
        width: '80%',
        marginBottom: '20px',
        borderRadius: '4px',
        border: '1px solid #ddd'
    };

    const buttonStyle = {
        padding: '10px 20px',
        fontSize: '16px',
        cursor: 'pointer',
        backgroundColor: '#007bff',
        color: 'white',
        border: 'none',
        borderRadius: '4px'
    };

    return (
        <div style={containerStyle}>
            <h2 style={{ marginBottom: '20px' }}>Welcome to the Chat</h2>
            <p style={{ marginBottom: '20px', color: '#555' }}>Please enter a nickname to join.</p>
            <input
                style={inputStyle}
                type="text"
                placeholder="Your Nickname"
                value={nickname}
                onChange={(e) => setNickname(e.target.value)}
                onKeyPress={handleKeyPress}
            />
            <button style={buttonStyle} onClick={enterChat}>Enter Chat Room</button>
        </div>
    );
};
