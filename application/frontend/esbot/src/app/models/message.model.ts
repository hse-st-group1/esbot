export interface Message {
  messageID: string,
  sessionID: string,
  messageContent: string,
  timestamp: string,
  sender: boolean,
  messageType: string
}