const { danger, markdown } = require("danger");

async function summarizePullRequest() {
  // Get the pull request title and body
  const prTitle = danger.github.pr.title;
  const prBody = danger.github.pr.body;

  // Get the modified and created files
  const modifiedFiles = danger.git.modified_files;
  const createdFiles = danger.git.created_files;

  // Generate a summary
  const summary = `
### Pull Request Summary
- **Title**: ${prTitle}
- **Description**: ${prBody || "No description provided."}
- **Modified Files**: ${modifiedFiles.length > 0 ? modifiedFiles.join(", ") : "None"}
- **Created Files**: ${createdFiles.length > 0 ? createdFiles.join(", ") : "None"}
`;

  // Post the summary as a comment on the pull request
  markdown(summary);
}

summarizePullRequest();