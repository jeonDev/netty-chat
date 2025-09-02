const { useState } = React;
const { useHistory } = ReactRouterDOM;

const HomePage = () => {
    const [nickname, setNickname] = useState('');
    const history = useHistory();

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

    return (
        <div className="home-page">
            <h2>Welcome to Chat</h2>
            <input
                type="text"
                placeholder="Enter your nickname"
                value={nickname}
                onChange={(e) => setNickname(e.target.value)}
                onKeyPress={handleKeyPress}
            />
            <button onClick={enterChat}>Enter Chat Room</button>
        </div>
    );
};
