// @ts-ignore
import { Request, Response } from 'express';

export default {
  'GET /api/blog/article/headlines': (req: Request, res: Response) => {
    res.status(200).send({});
  },
};
