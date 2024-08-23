# Task Tracker

A simple, lightweight Task Tracker application implemented in Java with a Command Line Interface (CLI). The project is designed to help users manage and track their tasks, offering features like adding, updating, viewing, and deleting tasks, all while storing data in a JSON file, ensuring your tasks are saved between sessions.

## Features

- **Task Management:** Create, update, view, and delete tasks.
- **Task Status Tracking:** Tasks can be marked as Todo, In-Progress, or Done using an intuitive CLI.
- **Persistent Storage:** Tasks are saved to a tasks.json file for persistent data storage.
- **Formatted Output:** Displays tasks in a clean, well-organized table format within the terminal.
- **Minimal Dependencies:** No external libraries are used, ensuring the project is simple and lightweight.

## How to Run

1. Clone the repository:

    ```bash
    git clone https://github.com/Farnam-Hs/Task-Tracker.git
    cd Task-Tracker
    ```

2. Compile and run `Application.java`

## Usage Example



```
+-------+------------------------------------------+--------------+------------------+------------------+
| ID    | Description                              | Status       | Created Time     | Updated Time     |
+-------+------------------------------------------+--------------+------------------+------------------+
| 1     | Finish documentation                     | TODO         | 2024-08-21 10:00 | 2024-08-21 10:00 |
| 2     | Implement CLI                            | IN_PROGRESS  | 2024-08-21 10:15 | 2024-08-21 11:00 |
+-------+------------------------------------------+--------------+------------------+------------------+
```


Sample solution for the [Task Tracker](https://roadmap.sh/projects/task-tracker) challenge from [roadmap.sh](https://roadmap.sh/).

