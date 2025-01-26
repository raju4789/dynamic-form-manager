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
- **Description**: ${prBody || "No description provided."}
`;

  // Summarize changes in modified files
  if (modifiedFiles.length > 0) {
    summary += `\n#### Modified Files:\n`;
    for (const file of modifiedFiles) {
      const diff = await danger.git.diffForFile(file);
      if (diff) {
        summary += `- **${file}**: Updated with changes. Key highlights:\n`;
        if (diff.added > 0) {
          summary += `  - Added ${diff.added} lines.\n`;
        }
        if (diff.deleted > 0) {
          summary += `  - Removed ${diff.deleted} lines.\n`;
        }
      }
    }
  }

  // Summarize changes in created files
  if (createdFiles.length > 0) {
    summary += `\n#### Created Files:\n`;
    for (const file of createdFiles) {
      summary += `- **${file}**: New file added.\n`;
    }
  }

  // Add a human-readable summary for specific types of changes
  summary += `\n### Human-Readable Summary:\n`;
  if (createdFiles.some(file => file.includes("Controller"))) {
    summary += `- Implemented new controllers to handle specific operations.\n`;
  }
  if (createdFiles.some(file => file.includes("ServiceImpl"))) {
    summary += `- Developed new service implementations for business logic.\n`;
  }
  if (modifiedFiles.some(file => file.includes("test"))) {
    summary += `- Added or updated unit tests to ensure functionality and error handling.\n`;
  }
  if (modifiedFiles.some(file => file.includes("UI") || file.includes("frontend"))) {
    summary += `- Updated UI components to integrate new functionality.\n`;
  }
  if (modifiedFiles.some(file => file.includes("config") || file.includes(".yml"))) {
    summary += `- Added or updated configuration files to support new features.\n`;
  }

  // Post the summary as a comment on the pull request
  markdown(summary);
}

summarizePullRequest();