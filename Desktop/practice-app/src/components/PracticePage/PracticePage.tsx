import React, { useState } from "react";
import "./PracticePage.css";
import { Entry } from "../../types/entry";
import { useLocalStorage } from "../../hooks/useLocalStorage";
import { v4 as uuidv4 } from "uuid";

const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

const PracticePage: React.FC = () => {
  const [entries, setEntries] = useLocalStorage<Entry[]>("practice_entries", []);
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [error, setError] = useState<string | null>(null);
  const [count, setCount] = useLocalStorage<number>("practice_counter", 0);

  const handleAdd = (e: React.FormEvent) => {
    e.preventDefault();
    setError(null);

    if (name.trim().length < 2) {
      setError("The name must be at least two letters.");
      return;
    }
    if (!emailRegex.test(email)) {
      setError("Enter a valid email.");
      return;
    }

    const newEntry: Entry = {
      id: uuidv4(),
      name: name.trim(),
      email: email.trim(),
      createdAt: new Date().toISOString(),
    };

    setEntries([...entries, newEntry]);
    setName("");
    setEmail("");
    setCount(count + 1);
  };

  const handleRemove = (id: string) => {
    setEntries(entries.filter((en) => en.id !== id));
  };

  const handleClearAll = () => {
    setEntries([]);
    setCount(0);
  };

  return (
    <div className="practice">
      <h1>A small training page</h1>
      <form onSubmit={handleAdd}>
        <input
          value={name}
          onChange={(e) => setName(e.target.value)}
          placeholder="name"
          aria-label="name"
        />
        <input
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          placeholder="email"
          aria-label="email"
        />
        <button type="submit" className="primary">Add</button>
        <button type="button" onClick={() => { setName(""); setEmail(""); }}>delete fields</button>
      </form>

      {error && <div className="error">{error}</div>}

      <div style={{ marginTop: 12, textAlign: "center" }}>
        <strong>Total number of entries:</strong> {count}
      </div>

      <ul>
        {entries.length === 0 && <li style={{ justifyContent: "center", color: "#666" }}>No data Yet.</li>}
        {entries.map((en) => (
          <li key={en.id}>
            <div>
              <div><strong>{en.name}</strong> — <span className="meta">{en.email}</span></div>
              <div className="meta">Added : {new Date(en.createdAt).toLocaleString()}</div>
            </div>
            <div className="actions">
              <button onClick={() => navigator.clipboard?.writeText(`${en.name} <${en.email}>`)}>Copy</button>
              <button onClick={() => handleRemove(en.id)}>Delete</button>
            </div>
          </li>
        ))}
      </ul>

      {entries.length > 0 && (
        <div style={{ textAlign: "center", marginTop: 12 }}>
          <button onClick={handleClearAll}>Delete all</button>
        </div>
      )}
    </div>
  );
};

export default PracticePage;