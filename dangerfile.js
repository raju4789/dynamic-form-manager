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
- **Modified Files**: ${modifiedFiles.length > 0 ? modifiedFiles.join(", ") : "None"}
- **Created Files**: ${createdFiles.length > 0 ? createdFiles.join(", ") : "None"}
`;

  // Analyze changes in each modified file
  if (modifiedFiles.length > 0) {
    summary += `\n\n### Changes in Modified Files:\n`;
    for (const file of modifiedFiles) {
      const diff = await danger.git.diffForFile(file);
      if (diff) {
        summary += `- **${file}**:\n\`\`\`diff\n${diff.diff.substring(0, 500)}\n\`\`\`\n`; // Limit diff to 500 characters
      }
    }
  }

  // Analyze changes in each created file
  if (createdFiles.length > 0) {
    summary += `\n\n### Changes in Created Files:\n`;
    for (const file of createdFiles) {
      const diff = await danger.git.diffForFile(file);
      if (diff) {
        summary += `- **${file}**:\n\`\`\`diff\n${diff.diff.substring(0, 500)}\n\`\`\`\n`; // Limit diff to 500 characters
      }
    }
  }

  // Post the summary as a comment on the pull request
  markdown(summary);
}

summarizePullRequest();