import path from "path"
import dotenv from "dotenv"

dotenv.config({path: path.resolve(__dirname,"../.env")});

interface ENV {
    GOOGLE_MAPS_API_KEY: string | undefined;
    PORT: number | undefined;
    MONGODB_URL: string | undefined;
    JWT_KEY: string | undefined;
}
  
interface Config {
    GOOGLE_MAPS_API_KEY: string;
    PORT: number;
    MONGODB_URL: string;
    JWT_KEY: string;
}
  
// Loading process.env as ENV interface
  
const getConfig = (): ENV => {
    return {
        GOOGLE_MAPS_API_KEY: process.env.GOOGLE_MAPS_API_KEY,
        PORT: process.env.PORT ? Number(process.env.PORT) : undefined,
        MONGODB_URL: process.env.MONGODB_URL,
        JWT_KEY: process.env.JWT_KEY
    };
};

// Throwing an Error if any field was undefined we don't 
// want our app to run if it can't connect to DB and ensure 
// that these fields are accessible. If all is good return
// it as Config which just removes the undefined from our type 
// definition.

const getSanitzedConfig = (config: ENV): Config => {
    for (const [key, value] of Object.entries(config)) {
        if (value === undefined) {
            throw new Error(`Missing key ${key} in config.env`);
        }
    }
    return config as Config;
};

const config = getConfig();

const sanitizedConfig = getSanitzedConfig(config);

export default sanitizedConfig;