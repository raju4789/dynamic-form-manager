const { danger, markdown } = require("danger");

async function summarizePullRequest() {
  // Get the pull request title and body
  const prTitle = danger.github.pr.title;
  const prBody = danger.github.pr.body;

  // Get the modified and created files
  const modifiedFiles = danger.git.modified_files;
  const createdFiles = danger.git.created_files;

  // Initialize the summary
  let summary = `
### Pull Request Summary
- **Title**: ${prTitle}
`;

  // Generate a description based on changes
  let description = "This pull request includes the following changes:\n";

  if (createdFiles.some(file => file.includes("Controller"))) {
    description += "- Implemented new controllers to handle specific operations, including error handling and logging.\n";
  }
  if (createdFiles.some(file => file.includes("ServiceImpl"))) {
    description += "- Developed new service implementations to handle business logic and integrate with external APIs.\n";
  }
  if (modifiedFiles.some(file => file.includes("test"))) {
    description += "- Added or updated unit tests to ensure functionality and proper error handling.\n";
  }
  if (modifiedFiles.some(file => file.includes("UI") || file.includes("frontend"))) {
    description += "- Updated UI components to integrate new functionality and improve user experience.\n";
  }
  if (modifiedFiles.some(file => file.includes("config") || file.includes(".yml"))) {
    description += "- Added or updated configuration files to support new features and ensure compatibility.\n";
  }
  if (modifiedFiles.some(file => file.includes("email") || file.includes("notification"))) {
    description += "- Enhanced email or notification functionality, including logging and error handling.\n";
  }

  // Add a fallback description if no specific patterns are matched
  if (description === "This pull request includes the following changes:\n") {
    description += "- General updates and improvements were made in this pull request.\n";
  }

  // Add the description to the summary
  summary += `- **Description**: ${description}\n`;

  // Add a human-readable summary
  summary += `
### Human-Readable Summary:
${description}
`;

  // Post the summary as a comment on the pull request
  markdown(summary);
}

summarizePullRequest();