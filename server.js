const express = require('express');
const path = require('path');
const fs = require('fs/promises');
const app = express();
const port = process.env.PORT || 3000;
const dataDir = path.join(__dirname, 'data');

const readJSON = async (filename, fallback = []) => {
  try {
    const file = path.join(dataDir, filename);
    const content = await fs.readFile(file, 'utf8');
    return JSON.parse(content || '[]');
  } catch (error) {
    return fallback;
  }
};

const writeJSON = async (filename, data) => {
  const file = path.join(dataDir, filename);
  await fs.writeFile(file, JSON.stringify(data, null, 2), 'utf8');
};

app.use(express.json());
app.use(express.static(path.join(__dirname)));

app.get('/api/status', (req, res) => {
  res.json({ status: 'ok', message: 'GeminiOS backend running' });
});

app.get('/api/apps', async (req, res) => {
  const apps = await readJSON('apps.json', []);
  res.json(apps);
});

app.get('/api/files', async (req, res) => {
  const files = await readJSON('files.json', []);
  res.json(files);
});

app.get('/api/chat', async (req, res) => {
  const messages = await readJSON('chat.json', []);
  res.json(messages);
});

app.post('/api/chat', async (req, res) => {
  const { author = '你', message } = req.body;
  if (!message || typeof message !== 'string') {
    return res.status(400).json({ error: 'Invalid message' });
  }
  const messages = await readJSON('chat.json', []);
  const newEntry = {
    id: Date.now(),
    author,
    message,
    timestamp: new Date().toISOString()
  };
  messages.push(newEntry);
  await writeJSON('chat.json', messages);
  res.status(201).json(newEntry);
});

app.listen(port, () => {
  console.log(`Server running at http://localhost:${port}`);
});
