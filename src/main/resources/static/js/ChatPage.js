const { useState, useEffect, useRef, useContext } = React;

const ChatPage = () => {
    const location = ReactRouterDOM.useLocation();
    const history = ReactRouterDOM.useHistory();
    
    // Get nickname from navigation state or redirect if it doesn't exist
    const [nickname, setNickname] = useState((location.state && location.state.nickname) || '');
    const [message, setMessage] = useState('');
    
    // Get WebSocket context from App.js
    const { ws, messages, send } = useContext(WebSocketContext);
    const messageAreaRef = useRef(null);

    useEffect(() => {
        // If the user lands here without a nickname, send them back to the home page
        if (!nickname) {
            history.replace('/');
        }
    }, [nickname, history]);

    // Effect to auto-scroll to the latest message
    useEffect(() => {
        if (messageAreaRef.current) {
            messageAreaRef.current.scrollTop = messageAreaRef.current.scrollHeight;
        }
    }, [messages]);

    const handleSendMessage = () => {
        if (message.trim() === "") return;

        const data = { sender: nickname, message: message };
        send(data); // Use the send function from context
        setMessage(''); // Clear the input field
    };

    const handleKeyPress = (event) => {
        if (event.key === 'Enter') {
            handleSendMessage();
        }
    };

    // Don't render anything if the nickname isn't set
    if (!nickname) return null;

    return (
        <div>
            <h2>Chat Room (Hi, {nickname})</h2>
            <div className="message-area" ref={messageAreaRef}>
                {messages.map((msg, index) => {
                    // System messages
                    if (msg.type === 'system') {
                        return <div key={index} className="message system-message">{msg.message}</div>;
                    }

                    // User messages
                    // Determine if the message is from the current user
                    const isMine = msg.sender === nickname && msg.isMine;
                    const messageClass = isMine ? 'my-message' : 'other-message';
                    
                    // Don't show messages sent by me that came back from the server
                    if (msg.sender === nickname && !msg.isMine) {
                        return null;
                    }

                    return (
                        <div key={index} className={`message ${messageClass}`}>
                            <div className="content">
                                {!isMine && <span className="sender">{msg.sender}</span>}
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
                <button onClick={handleSendMessage}>Send</button>
            </div>
        </div>
    );
};
