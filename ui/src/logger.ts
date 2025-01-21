import log from "loglevel";

// Save the original methodFactory
const originalFactory = log.methodFactory;

// Set the default log level (can be adjusted based on environment)
log.setLevel(process.env.NODE_ENV === "production" ? "warn" : "debug");

// Customize log levels to add prefixes
log.methodFactory = function (methodName, logLevel, loggerName) {
  const rawMethod = originalFactory(methodName, logLevel, loggerName); // Use the original factory
  return function (message: string) {
    rawMethod(`[${methodName.toUpperCase()}] ${message}`);
  };
};

// Apply the custom method factory
log.setLevel(log.getLevel());

export default log;