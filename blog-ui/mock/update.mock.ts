// @ts-ignore
import { Request, Response } from 'express';

export default {
  'PUT /api/blog/article': (req: Request, res: Response) => {
    res.status(200).send({});
  },
};
